package com.exclusively.aggregator.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * Catalog(Node) controller, fetches Catalog info from the microservice
 * 
 * @author Anshul Chauhan
 */
@Service
public class CatalogAggregatorService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(CatalogAggregatorService.class.getName());

	public CatalogAggregatorService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	@HystrixCommand(fallbackMethod = "defaultResponse")
	public String findCategoryByCatId(final String catId) {
		String result = restTemplate.getForObject(serviceUrl + "/product/id/{number}", String.class, catId);
		return result;
		
	}

	@HystrixCommand(fallbackMethod = "defaultResponse")
	public String findProducts(String params) {
		String url = serviceUrl + "/category/productlist/"+params;
		String result = restTemplate.getForObject(url, String.class);
		return result;
	}
	private String defaultResponse(final String mlId) {
		return "";
	}
	

}
