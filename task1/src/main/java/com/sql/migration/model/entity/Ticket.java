package com.sql.migration.model.entity;

import lombok.Data;

@Data
public class Ticket {

    private Long id;
    private User user;
    private Event event;
    private Integer place;
    private Category category;

}
