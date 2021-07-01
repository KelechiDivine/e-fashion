package com.houseofo.data.model;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
@Data
@Document
public class Order {
    @Id
    private String id;
    private LocalDate dateOrdered;
    private Map<String, Item> items;
    private Address address;
    private boolean isCompleted;
}
