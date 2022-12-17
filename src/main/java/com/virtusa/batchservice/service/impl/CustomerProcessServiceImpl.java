package com.virtusa.batchservice.service.impl;

import com.virtusa.batchservice.entity.Customer;
import com.virtusa.batchservice.service.CustomerProcessService;

public class CustomerProcessServiceImpl implements CustomerProcessService {

    @Override
    public Customer process(Customer customer) throws Exception {
        int age = customer.getAge();
        if (age >= 10 && age < 20){
            customer.setPoints(5);
        } else if (age >= 20 && age <= 30){
            customer.setPoints(10);
        } else {
            customer.setPoints(0);
        }
        return customer;
    }
}
