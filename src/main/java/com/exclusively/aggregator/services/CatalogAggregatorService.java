package com.exclusively.aggregator.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exclusively.aggregator.entities.CartView;
import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * Catalog(Node) controller, fetches Catalog info from the microservice
 * 
 * @author Anshul Chauhan
 */
@Component
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
