package com.exclusively.aggregator.entities;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Order {

	private static final long serialVersionUID = 1L;

	protected Long id;

	protected String name;

	protected BigDecimal price;


}