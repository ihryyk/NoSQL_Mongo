package com.sql.migration.model.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserAccount {

    private Long id;
    private User user;
    private BigDecimal money;

}
