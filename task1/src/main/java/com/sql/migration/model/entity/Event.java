package com.sql.migration.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Long id;
    private String title;
    private Date date;
    private BigDecimal ticketPrice;
    private List<Ticket> tickets;
}
