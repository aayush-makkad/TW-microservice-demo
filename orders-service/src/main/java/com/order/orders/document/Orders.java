package com.order.orders.document;

import java.sql.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "orders")
public class Orders {

	@Id
	private String id;
	
	private String productId;
	
	private String userId;
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	@JsonIgnore
	public String get_class() {
		return _class;
	}

	@JsonIgnore
	private String _class;
	
	
	
}
