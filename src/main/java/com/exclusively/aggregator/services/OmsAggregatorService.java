package com.exclusively.aggregator.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exclusively.aggregator.entities.Order;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class OmsAggregatorService {

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl;

	protected Logger logger = Logger.getLogger(OmsAggregatorService.class.getName());

	public OmsAggregatorService(String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
	}

	@HystrixCommand(fallbackMethod = "defaultOrder")
	public Order findByOrderNumber(final String orderNo) {
				return restTemplate.getForObject(serviceUrl + "/orders/{number}", Order.class, orderNo);
	}

	private Order defaultOrder(final String mlId) {
		Order order = new Order();
		order.setId(-1L);
		order.setName("Interesting...the wrong title. Sssshhhh!");
		return order;
	}

}
