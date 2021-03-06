package com.exclusively.aggregator.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * Catalog(Node) controller, fetches Catalog info from the microservice
 * 
 * @author Anshul Chauhan
 */

public class CatalogAggregatorService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(CatalogAggregatorService.class.getName());

	public CatalogAggregatorService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	@HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
        }, fallbackMethod = "findCategoryByCatIdFallBack")
	public String findCategoryByCatId(String catId) {
		String result = restTemplate.getForObject(serviceUrl + "/orders/{number}", String.class, catId);
		return result;
		
	}

	@HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
        }, fallbackMethod = "defaultResponse")
	public String findProducts(String params) {
		String url = serviceUrl + "/category/productlist/"+params;
		String result = restTemplate.getForObject(url, String.class);
		return result;
	}
	@HystrixCommand
	private String findCategoryByCatIdFallBack(String mlId) {
		return "Default response";
	}



}
