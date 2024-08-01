package com.sql.migration.model.document;

import lombok.Data;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Event {

    private ObjectId id;
    private String title;
    private Date date;
    private BigDecimal ticketPrice;

}
