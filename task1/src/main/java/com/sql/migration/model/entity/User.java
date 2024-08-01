package com.sql.migration.model.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {

    private Long id;
    private String name;
    private String email;
    private List<Ticket> tickets;
    private UserAccount userAccount;

}

