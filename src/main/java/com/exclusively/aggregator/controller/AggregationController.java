package com.exclusively.aggregator.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.services.CatalogAggregatorService;

/**
 * Client controller, fetches Order info from the microservice
 * 
 * @author Anshul Chauhan
 */
@RestController
public class AggregationController {

	@Autowired
	protected CatalogAggregatorService catalogService;

	protected Logger logger = Logger.getLogger(AggregationController.class.getName());

	@RequestMapping("/")
	public String goHome() {
		return "index";
	}

	@RequestMapping(value = "/category/id/{catId}")
	public @ResponseBody String getByCategoryId(@PathVariable("catId") String catId) {

		logger.info("Catalog service invoked with params: " + catId);

		String account = catalogService.findCategoryByCatId(catId);
		
		return account;
	}
	
	@RequestMapping(value = "/category/productlist/{params}")
	public @ResponseBody String getProductList(@PathVariable("params") String params) {

		logger.info("Catalog service invoked with params: " + params);

		String account = catalogService.findProducts(params);
		
		return account;
	}
}
