package com.virtusa.batchservice.service;

import com.virtusa.batchservice.entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public interface CustomerProcessService extends ItemProcessor<Customer, Customer> {
}
