package com.product.catalogue.repository;

import com.product.catalogue.document.Availability;

public interface DataAccessLayerRepoClass {
	
	public Availability getAvailabilityInfo(String productId);

}
