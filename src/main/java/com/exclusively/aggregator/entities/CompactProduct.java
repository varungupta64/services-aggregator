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

	
}