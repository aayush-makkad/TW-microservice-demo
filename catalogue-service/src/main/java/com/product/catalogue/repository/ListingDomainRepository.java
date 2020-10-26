package com.product.catalogue.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.product.catalogue.document.Listings;

@Repository
public interface ListingDomainRepository extends MongoRepository<Listings, String>{
	
}
