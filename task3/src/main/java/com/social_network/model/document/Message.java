package com.social_network.model.document;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;

@Data
public class Message {

    private ObjectId id;
    private ObjectId sender;
    private ObjectId receiver;
    private String text;
    private Date sentAt;

}
