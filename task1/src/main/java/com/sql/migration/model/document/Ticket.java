package com.sql.migration.model.document;

import com.sql.migration.model.entity.Category;
import com.sql.migration.model.entity.Event;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Ticket {

    private ObjectId id;
    private Event event;
    private Integer place;
    private Category category;

}
