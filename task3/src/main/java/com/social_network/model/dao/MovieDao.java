package com.social_network.model.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.social_network.model.document.Movie;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Optional;

public class MovieDao {

    private final String MONGODB_URL = "mongodb://localhost:27017";

    public MovieDao() {}

    public Movie save(Movie movie) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("social_network");
            Document doc = new Document()
                    .append("title", movie.getTitle())
                    .append("year", movie.getYear())
                    .append("genre", movie.getGenre());
            mongoDatabase.getCollection("movies").insertOne(doc);
            ObjectId id = doc.getObjectId("_id");
            movie.setId(id);
            return movie;
        }
    }

    public Optional<Movie> getMovieById(ObjectId id) {
        try (MongoClient mongoClient = MongoClients.create(MONGODB_URL)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase("social_network");
            Document doc = mongoDatabase.getCollection("movies").find(Filters.eq("_id", id)).first();
            if (doc == null) {
                return Optional.empty();
            }
            Movie movie = new Movie();
            movie.setId(doc.getObjectId("_id"));
            movie.setTitle(doc.getString("title"));
            movie.setYear(doc.getInteger("year"));
            movie.setGenre(doc.getString("genre"));
            return Optional.of(movie);
        }
    }

}
