package com.product.catalogue.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "listings")
public class Listings {
	
	@Id
	private String id;
	
	private String name;
	
	private int price;
	
	private String _class;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Inventory [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
	
	
	
	

}
