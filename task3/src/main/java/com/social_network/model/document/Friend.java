package com.social_network.model.document;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
public class Friend {

    private ObjectId userId;
    private Date friendshipStarted;
}
