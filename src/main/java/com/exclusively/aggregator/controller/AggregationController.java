package com.exclusively.aggregator.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.entities.Order;
import com.exclusively.aggregator.services.OmsAggregatorService;

/**
 * Client controller, fetches Order info from the microservice
 * 
 * @author Anshul Chauhan
 */
@RestController
public class AggregationController {

	@Autowired
	protected OmsAggregatorService orderService;

	protected Logger logger = Logger.getLogger(AggregationController.class.getName());

	@RequestMapping("/")
	public String goHome() {
		return "index";
	}

	@RequestMapping(value = "/orders/{orderNo}")
	public @ResponseBody Order byNumber(@PathVariable("orderNo") String orderNo) {

		logger.info("web-service byNumber() invoked: " + orderNo);

		Order account = orderService.findByOrderNumber(orderNo);
		logger.info("web-service byNumber() found: " + account);
		return account;
	}

	
//	@RequestMapping(value = "/orders/{orderNo}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
//	    public DeferredResult<Order> movieDetails(@PathVariable String orderNo) {
//	        Observable<Order> details = Observable.zip(
//	                orderService.findByOrderNumber(orderNo),
//	                (order) -> {
//	                    return order;
//	                }
//	        );
//	        return toDeferredResult(details);
//	    }
//
//	    public DeferredResult<Order> toDeferredResult(Observable<Order> details) {
//	        DeferredResult<Order> result = new DeferredResult<>();
//	        details.subscribe(new Observer<Order>() {
//	            @Override
//	            public void onCompleted() {
//	            }
//
//	            @Override
//	            public void onError(Throwable throwable) {
//	            }
//
//	            @Override
//	            public void onNext(Order movieDetails) {
//	                result.setResult(movieDetails);
//	            }
//	        });
//	        return result;
//	    }
	    
	    
}
