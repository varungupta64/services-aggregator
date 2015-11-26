package com.exclusively.aggregator.entities;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Order {

	private static final long serialVersionUID = 1L;

	protected Long id;

	protected String name;

	protected BigDecimal price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}