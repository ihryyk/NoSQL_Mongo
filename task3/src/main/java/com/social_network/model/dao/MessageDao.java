package com.social_network.model.dao;

import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.social_network.model.document.Message;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Optional;

public class MessageDao {
    private final String MONGODB_URL = "mongodb://localhost:27017";

    public MessageDao() {
    }

    public Message save(Message message) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("social_network");
            Document doc = new Document()
                    .append("sender", message.getSender())
                    .append("receiver", message.getReceiver())
                    .append("text", message.getText())
                    .append("sentAt", message.getSentAt());
            mongoDatabase.getCollection("messages").insertOne(doc);
            ObjectId id = doc.getObjectId("_id");
            message.setId(id);
            return message;
        }
    }

    public Optional<Message> getMessageById(ObjectId id) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("social_network");
            Document doc = mongoDatabase.getCollection("messages").find(Filters.eq("_id", id)).first();
            if (doc == null) {
                return Optional.empty();
            }
            Message message = new Message();
            message.setId(doc.getObjectId("_id"));
            message.setSender(doc.getObjectId("sender"));
            message.setReceiver(doc.getObjectId("receiver"));
            message.setText(doc.getString("text"));
            message.setSentAt(doc.getDate("sentAt"));
            return Optional.of(message);
        }
    }

    public double getAverageNumberOfMessagesOnDayOfWeek(DayOfWeek dayOfWeek) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("social_network");
            MongoCollection<Document> messagesCollection = mongoDatabase.getCollection("messages");
            AggregateIterable<Document> result = messagesCollection.aggregate(
                    Arrays.asList(
                            Aggregates.addFields(
                                    new Field<>("dayOfWeek", new Document("$dayOfWeek", "$sentAt")),
                                    new Field<>("date", new Document("$dateToString", new Document("format", "%Y-%m-%d").append("date", "$sentAt")))
                            ),
                            Aggregates.match(Filters.eq("dayOfWeek", dayOfWeek.getValue())),
                            Aggregates.group(new Document("dayOfWeek", "$dayOfWeek").append("date", "$date"), Accumulators.sum("messagesOnDay", 1)),
                            Aggregates.group("$_id.dayOfWeek", Accumulators.avg("averageMessages", "$messagesOnDay"))
                    )
            );
            Document firstResult = result.first();
            if (firstResult == null) {
                throw new RuntimeException("No result found in getAverageNumberOfMessagesOnDayOfWeek aggregation");
            }
            return firstResult.getDouble("averageMessages");
        }
    }
}
