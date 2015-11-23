package com.exclusively.aggregator.server;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.config.EnableIntegration;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;
import com.exclusively.aggregator.controller.AggregationController;
import com.exclusively.aggregator.controller.CartAggregationController;
import com.exclusively.aggregator.services.CartAggregatorService;
import com.exclusively.aggregator.services.CatalogAggregatorService;

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
//@EnableZuulProxy
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
	public CartAggregatorService cartService() {
		return new CartAggregatorService();
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
	
	/**
	 * The AccountService encapsulates the interaction with the micro-service.
	 * 
	 * @return A new service instance.
	 */
	@Bean
	public CartAggregationController cartController() {
		return new CartAggregationController();
	}
	
//	@Bean
//	public PollScheduler runMonitoring() {
//		HystrixPlugins.getInstance().registerMetricsPublisher(HystrixServoMetricsPublisher.getInstance());
//
//		
//
//		// Minimal Servo configuration for publishing to Graphite
//		final List<MetricObserver> observers = new ArrayList<MetricObserver>();
//
//		observers.add(new GraphiteMetricObserver("Test", "10.30.59.201:2003"));
//		PollScheduler.getInstance().start();
//		PollRunnable task = new PollRunnable(new MonitorRegistryMetricPoller(), BasicMetricFilter.MATCH_ALL, true, observers);
//		PollScheduler scheduler = PollScheduler.getInstance();
//				scheduler.addPoller(task, 5, TimeUnit.SECONDS);
//		return scheduler;
//	}
//	@Bean
//	public GraphiteReporter graphiteReporter(MetricRegistry metricRegistry) {
//	    final GraphiteReporter reporter = GraphiteReporter
//	            .forRegistry(metricRegistry)
//	            .build(graphite());
//	    reporter.start(1, TimeUnit.SECONDS);
//	    return reporter;
//	}
//	 
//	@Bean
//	GraphiteSender graphite() {
//	    return new Graphite(new InetSocketAddress("10.11.19.18", 2003));
//	}
}
