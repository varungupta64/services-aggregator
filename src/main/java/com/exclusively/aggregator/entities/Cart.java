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

}
