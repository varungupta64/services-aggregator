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

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCouponApplied() {
		return couponApplied;
	}

	public void setCouponApplied(String couponApplied) {
		this.couponApplied = couponApplied;
	}

	public float getDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(float discountApplied) {
		this.discountApplied = discountApplied;
	}

	public float getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(float netPrice) {
		this.netPrice = netPrice;
	}

	public Map<CompactProduct, Integer> getProductQuantityMapping() {
		return productQuantityMapping;
	}

	public void setProductQuantityMapping(Map<CompactProduct, Integer> productQuantityMapping) {
		this.productQuantityMapping = productQuantityMapping;
	}
	
<<<<<<< Updated upstream
=======
	
>>>>>>> Stashed changes
	
}
