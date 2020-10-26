package com.product.catalogue.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "availability")
public class Availability {
	
	@Id
	private String id;
	
	private String listingId;
	
	private int quantity;
	
	private String _class;

	public String getListingId() {
		return listingId;
	}

	public void setListingId(String listingId) {
		this.listingId = listingId;
	}

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Availability [id=" + id + ", listingId=" + listingId + ", quantity=" + quantity + "]";
	}
	
	

}
