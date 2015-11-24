package com.exclusively.aggregator.server;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;
import com.exclusively.aggregator.services.CatalogAggregatorService;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
@EnableIntegration
@EnableSwagger2
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
//	@Bean
//	public CatalogAggregatorService orderService() {
//		return new CatalogAggregatorService(OMS_SERVICE_URL);
//	}

	/**
	 * The AccountService encapsulates the interaction with the micro-service.
	 * 
	 * @return A new service instance.
	 */
//	@Bean
//	public CartAggregatorService cartService() {
//		return new CartAggregatorService();
//	}
		
	/**
	 * The AccountService encapsulates the interaction with the micro-service.
	 * 
	 * @return A new service instance.
	 */
//	@Bean
//	public AggregationController aggregatorController() {
//		return new AggregationController();
//	}
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
	@Bean
	public GraphiteReporter graphiteReporter(MetricRegistry metricRegistry) {
		final GraphiteReporter reporter = GraphiteReporter
				.forRegistry(metricRegistry)
				.build(graphite());
		reporter.start(1, TimeUnit.SECONDS);
		return reporter;
	}

	@Bean
	GraphiteSender graphite() {
		return new Graphite(new InetSocketAddress("10.11.19.18", 2003));
	}

	@Bean
	public Docket petApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.directModelSubstitute(LocalDate.class,
						String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(
						AlternateTypeRules.newRule(typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
								typeResolver.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET,
						Lists.newArrayList(new ResponseMessageBuilder()
								.code(500)
								.message("500 message")
								.responseModel(new ModelRef("Error"))
								.build()))
				.securitySchemes(Lists.newArrayList(apiKey()))
				.securityContexts(Lists.newArrayList(securityContext()))
				//.enableUrlTemplating(true)
				;
	}

	@Autowired
	private TypeResolver typeResolver;

	private ApiKey apiKey() {
		return new ApiKey("mykey", "api_key", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/anyPath.*"))
				.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
		= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(
				new SecurityReference("mykey", authorizationScopes));
	}

	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(
				"test-app-client-id",
				"test-app-realm",
				"test-app",
				"apiKey");
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl");
	}

	
//	/**
//	 * The AccountService encapsulates the interaction with the micro-service.
//	 * 
//	 * @return A new service instance.
//	 */
//	@Bean
//	public CartAggregationController cartController() {
//		return new CartAggregationController();
//	}
	
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
