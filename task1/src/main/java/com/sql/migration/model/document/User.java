package com.sql.migration.model.document;

import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.List;

@Data
public class User {

    private ObjectId id;
    private String name;
    private String email;
    private BigDecimal money;
    private List<Ticket> tickets;
}
