package com.sql.migration.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MigrationService {

    private final MongoDatabase mongoDatabase;

    private final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/ticket_booking?user=postgres&password=password";
    private final String MONGODB_URL = "mongodb://localhost:27017";
    private final String SELECT_USERS = "SELECT u.id, u.name, u.email, a.money FROM users u " +
            "JOIN user_accounts a ON u.id = a.user_id";
    private final String SELECT_TICKETS_BY_USER_ID = "SELECT * FROM tickets WHERE user_id = ?";
    private final String SELECT_EVENT_BY_ID = "SELECT * FROM events WHERE id = ?";

    public MigrationService() {
        MongoClient mongoClient = MongoClients.create(MONGODB_URL);
        mongoDatabase = mongoClient.getDatabase("ticket_booking");
    }

    public void migrate() throws SQLException {
        try (Connection connection = DriverManager.getConnection(POSTGRES_URL)) {
            migrateUsersAndTickets(connection);
        }
    }

    private void migrateUsersAndTickets(Connection connection) throws SQLException {
        MongoCollection<Document> usersCollection = mongoDatabase.getCollection("users");
        try (PreparedStatement psUsers = connection.prepareStatement(SELECT_USERS);
             ResultSet rsUsers = psUsers.executeQuery()) {
            while (rsUsers.next()) {
                Document user = new Document()
                        .append("userId", rsUsers.getInt("id"))
                        .append("name", rsUsers.getString("name"))
                        .append("email", rsUsers.getString("email"))
                        .append("money", new Document("money", rsUsers.getBigDecimal("money")));
                usersCollection.insertOne(user);
                List<Document> tickets = getTicketsForUser(rsUsers.getInt("id"), connection);
                if (!tickets.isEmpty()) {
                    usersCollection.updateOne(new Document("userId", rsUsers.getInt("id")),
                            new Document("$set", new Document("tickets", tickets)));
                }
            }
        }
    }

    private List<Document> getTicketsForUser(int userId, Connection connection) throws SQLException {
        List<Document> tickets = new ArrayList<>();
        try (PreparedStatement psTickets = connection.prepareStatement(SELECT_TICKETS_BY_USER_ID)) {
            psTickets.setInt(1, userId);
            ResultSet rsTickets = psTickets.executeQuery();
            while (rsTickets.next()) {
                Document event = getEventForTicket(rsTickets.getInt("event_id"), connection);
                Document ticket = new Document()
                        .append("ticketId", rsTickets.getInt("id"))
                        .append("place", rsTickets.getInt("place"))
                        .append("category", rsTickets.getString("category"))
                        .append("event", event);
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    private Document getEventForTicket(int eventId, Connection connection) throws SQLException {
        Document event = new Document();
        try (PreparedStatement ps = connection.prepareStatement(SELECT_EVENT_BY_ID)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    event
                            .append("eventId", rs.getInt("id"))
                            .append("title", rs.getString("title"))
                            .append("date", rs.getDate("date"))
                            .append("ticket_price", rs.getBigDecimal("ticket_price"));
                }
            }
        }
        return event;
    }

}
