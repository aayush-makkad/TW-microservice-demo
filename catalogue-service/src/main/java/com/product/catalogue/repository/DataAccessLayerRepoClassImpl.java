package com.product.catalogue.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.product.catalogue.document.Availability;

@Repository
public class DataAccessLayerRepoClassImpl implements DataAccessLayerRepoClass {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Availability getAvailabilityInfo(String productId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("listingId").is(productId));
		return mongoTemplate.findOne(query, Availability.class);
		
	}

}
