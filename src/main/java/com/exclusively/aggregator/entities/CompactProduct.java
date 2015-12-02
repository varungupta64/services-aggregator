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
	
	private String type_id;
	
	private String tab_sizing;
	
	private String categoryId;
	
	private String floorId;
	
	private String imageUrl;
	
	private String url_key;
	
	private String price;
	
	private MediaGallery media_gallery;
	
}