package com.exclusively.aggregator.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
	
	private List<CompactProduct> compactProducts = new ArrayList<CompactProduct>();
	
//	private Map<CompactProduct,Integer> productQuantityMapping = null;
	

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

//	public Map<CompactProduct, Integer> getProductQuantityMapping() {
//		if(this.productQuantityMapping == null){
//			this.productQuantityMapping = new HashMap<CompactProduct,Integer>();
//		}
//		return productQuantityMapping;
//	}
//
//	public void setProductQuantityMapping(Map<CompactProduct, Integer> productQuantityMapping) {
//		this.productQuantityMapping = productQuantityMapping;
//	}
//	
	
}
