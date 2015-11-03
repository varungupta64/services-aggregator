package com.exclusively.aggregator.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.config.EnableIntegration;

import com.exclusively.aggregator.controller.AggregationController;
import com.exclusively.aggregator.services.CatalogAggregatorService;
import com.netflix.hystrix.contrib.servopublisher.HystrixServoMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.MetricObserver;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;
import com.netflix.servo.publish.graphite.GraphiteMetricObserver;

/**
 * Works as a microservice client, fetching data from the OMS-Service. Uses the
 * Discovery Server (Eureka) to find the microservice.
 * 
 * @author Anshul Chauhan
 */
@SpringBootApplication
@EnableAutoConfiguration
//@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan
@EnableZuulProxy
@EnableIntegration
public class AggregatorServer {

	public static final String OMS_SERVICE_URL = "http://FLOOR-API";

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
	
	@Bean
	public PollScheduler runMonitoring() {
		HystrixPlugins.getInstance().registerMetricsPublisher(HystrixServoMetricsPublisher.getInstance());

		

		// Minimal Servo configuration for publishing to Graphite
		final List<MetricObserver> observers = new ArrayList<MetricObserver>();

		observers.add(new GraphiteMetricObserver("Test", "10.30.59.201:2003"));
		PollScheduler.getInstance().start();
		PollRunnable task = new PollRunnable(new MonitorRegistryMetricPoller(), BasicMetricFilter.MATCH_ALL, true, observers);
		PollScheduler scheduler = PollScheduler.getInstance();
				scheduler.addPoller(task, 5, TimeUnit.SECONDS);
		return scheduler;
	}

}
