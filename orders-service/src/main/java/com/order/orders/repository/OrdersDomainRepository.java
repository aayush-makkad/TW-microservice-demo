package com.order.orders.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.order.orders.document.Orders;


public interface OrdersDomainRepository extends MongoRepository<Orders, String>{

}
