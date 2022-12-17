package com.virtusa.batchservice.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Customer {
    private long customerId;
    private String firstName;
    private int age;
    private int points;
}
