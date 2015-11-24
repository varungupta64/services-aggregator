package com.exclusively.aggregator.entities;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class CartView {

	private String id;
	
	private String created;
	
	private String updated;
	
	private boolean isGuest;
	
	private float totalPrice;
	
	private String couponApplied;
	
	private float discountApplied;
	
	private float netPrice;
	
	private Map<CompactProduct,Integer> productQuantityMapping = new HashMap<CompactProduct,Integer>();
	
}
