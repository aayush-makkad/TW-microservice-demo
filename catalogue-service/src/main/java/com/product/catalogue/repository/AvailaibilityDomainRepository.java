package com.product.catalogue.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.product.catalogue.document.Availability;

public interface AvailaibilityDomainRepository extends MongoRepository<Availability, String>{

}
