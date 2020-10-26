package com.product.catalogue.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductNotFoundException extends Throwable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1588855632001237859L;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductNotFoundException.class);
	
	public ProductNotFoundException(String productId) {
		super("poduct not found with id "+productId);
		logger.error("poduct not found with id "+productId);
	}

}
