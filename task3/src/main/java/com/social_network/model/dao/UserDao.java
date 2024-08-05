package com.social_network.model.dao;

import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.social_network.model.document.Friend;
import com.social_network.model.document.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class UserDao {

    private final String MONGODB_URL = "mongodb://localhost:27017";


    public User saveUser(User user) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("social_network");
            List<Document> friendDocuments = user.getFriends().stream()
                    .map(friend -> new Document()
                            .append("userId", friend.getUserId())
                            .append("friendshipStarted", friend.getFriendshipStarted()))
                    .collect(Collectors.toList());
            Document doc = new Document()
                    .append("username", user.getUsername())
                    .append("email", user.getEmail())
                    .append("friends", friendDocuments)
                    .append("watchedMovies", user.getWatchedMovies());

            mongoDatabase.getCollection("users").insertOne(doc);
            ObjectId id = doc.getObjectId("_id");
            user.setId(id);
            return user;
        }
    }

    public Optional<User> getUserById(ObjectId id) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("social_network");
            Document doc = mongoDatabase.getCollection("users").find(Filters.eq("_id", id)).first();
            if (doc == null) {
                return Optional.empty();
            }
            User user = new User();
            user.setId(doc.getObjectId("_id"));
            user.setUsername(doc.getString("username"));
            user.setEmail(doc.getString("email"));
            user.setFriends(getFriends(doc));
            user.setWatchedMovies(new HashSet<>((List<ObjectId>) doc.get("watchedMovies")));
            return Optional.of(user);
        }
    }

    private List<Friend> getFriends(Document doc) {
        List<Document> friendDocs = (List<Document>) doc.get("friends");
        return friendDocs.stream().map(friendDoc -> {
            Friend friend = new Friend();
            friend.setUserId(friendDoc.getObjectId("userId"));
            friend.setFriendshipStarted(friendDoc.getDate("friendshipStarted"));
            return friend;
        }).collect(Collectors.toList());
    }

    public int getNumberOfNewFriendships(LocalDateTime startMonth, LocalDateTime endMonth) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("social_network");
            MongoCollection<Document> usersCollection = mongoDatabase.getCollection("users");
            Date start = Date.from(startMonth.atZone(ZoneId.systemDefault()).toInstant());
            Date end = Date.from(endMonth.atZone(ZoneId.systemDefault()).toInstant());

            AggregateIterable<Document> result = usersCollection.aggregate(
                    Arrays.asList(
                            Aggregates.unwind("$friends"),
                            Aggregates.match(Filters.and(
                                    Filters.gte("friends.friendshipStarted", start),
                                    Filters.lt("friends.friendshipStarted", end)
                            )),
                            Aggregates.group(null, Accumulators.sum("newFriendships", 1))
                    )
            );
            Document firstResult = result.first();
            if (firstResult == null) {
                throw new RuntimeException("No result found in getNumberOfNewFriendships aggregation");
            }
            return firstResult.getInteger("newFriendships");
        }
    }


    public int getMinNumberOfWatchedMoviesByUsersWithAtLeast2Friends() {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("social_network");
            MongoCollection<Document> usersCollection = mongoDatabase.getCollection("users");
            AggregateIterable<Document> result = usersCollection.aggregate(Arrays.asList(
                    Aggregates.addFields(
                            new Field<>("watchedMoviesSize", new Document("$size", "$watchedMovies")),
                            new Field<>("friendsSize", new Document("$size", "$friends"))),
                    Aggregates.match(Filters.eq("friendsSize", 2)),
                    Aggregates.group(null, Accumulators.min("minWatchedMoviesSize", "$watchedMoviesSize"))
            ));
            Document firstResult = result.first();
            if (firstResult == null) {
                throw new RuntimeException("No result found in getMinNumberOfWatchedMoviesByUsersWithAtLeast2Friends aggregation");
            }
            return firstResult.getInteger("minWatchedMoviesSize");
        }
    }
}
