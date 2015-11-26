package com.exclusively.aggregator.entities;

import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Field;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class Cart {
	private String id;

	private String created;

	private String updated;

	private boolean isGuest;

	private Map<String, Integer> productQuantityMapping = new HashMap<String, Integer>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public boolean isGuest() {
		return isGuest;
	}

	public void setGuest(boolean isGuest) {
		this.isGuest = isGuest;
	}

	public Map<String, Integer> getProductQuantityMapping() {
		return productQuantityMapping;
	}

	public void setProductQuantityMapping(Map<String, Integer> productQuantityMapping) {
		this.productQuantityMapping = productQuantityMapping;
	}
	
	

}
