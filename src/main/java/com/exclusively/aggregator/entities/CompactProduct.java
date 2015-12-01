package com.exclusively.aggregator.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompactProduct {
	private String designer_brand_name_label;

	private String size_label;

	private String msrp;

	private String _id;

	private String name;

	private String qty;

	private String product_description;

	private String is_in_stock;

	private String entity_id;

	private String designer_brand_name_value;

	private String cost;

	private Integer currentQuantity;

	public String getDesigner_brand_name_label() {
		return designer_brand_name_label;
	}

	public void setDesigner_brand_name_label(String designer_brand_name_label) {
		this.designer_brand_name_label = designer_brand_name_label;
	}

	public String getSize_label() {
		return size_label;
	}

	public void setSize_label(String size_label) {
		this.size_label = size_label;
	}

	public String getMsrp() {
		return msrp;
	}

	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

	public String getIs_in_stock() {
		return is_in_stock;
	}

	public void setIs_in_stock(String is_in_stock) {
		this.is_in_stock = is_in_stock;
	}

	public String getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(String entity_id) {
		this.entity_id = entity_id;
	}

	public String getDesigner_brand_name_value() {
		return designer_brand_name_value;
	}

	public void setDesigner_brand_name_value(String designer_brand_name_value) {
		this.designer_brand_name_value = designer_brand_name_value;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompactProduct other = (CompactProduct) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		return result;
	}

}