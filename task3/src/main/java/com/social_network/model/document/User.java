package com.social_network.model.document;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class User {

    private ObjectId id;
    private String username;
    private String email;
    private List<Friend> friends = new ArrayList<>();
    private Set<ObjectId> watchedMovies = new HashSet<>();

}
