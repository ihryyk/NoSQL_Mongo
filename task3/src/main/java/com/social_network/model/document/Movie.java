package com.social_network.model.document;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Movie {

    private ObjectId id;
    private String title;
    private int year;
    private String genre;

}
