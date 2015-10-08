package com.exclusively.aggregator.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.exclusively.aggregator.controller.AggregationController;
import com.exclusively.aggregator.services.CatalogAggregatorService;

/**
 * Works as a microservice client, fetching data from the OMS-Service. Uses the
 * Discovery Server (Eureka) to find the microservice.
 * 
 * @author Anshul Chauhan
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan
@EnableZuulProxy
public class AggregatorServer {

	public static final String OMS_SERVICE_URL = "http://NODE-CATALOG-API";

	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		SpringApplication.run(AggregatorServer.class, args);
	}

	/**
	 * The AccountService encapsulates the interaction with the micro-service.
	 * 
	 * @return A new service instance.
	 */
	@Bean
	public CatalogAggregatorService orderService() {
		return new CatalogAggregatorService(OMS_SERVICE_URL);
	}

	/**
	 * The AccountService encapsulates the interaction with the micro-service.
	 * 
	 * @return A new service instance.
	 */
	@Bean
	public AggregationController aggregatorController() {
		return new AggregationController();
	}
}
