package com.exclusively.aggregator.zuul.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.MessageProducer;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class ProductPopulaityFilter extends ZuulFilter {
	
	@Override
	public Object run() {
		System.out.println("Filter Called");
		//TODO add kafka message logger
		return null;
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return RequestContext.getCurrentContext().getRequest().getRequestURI().endsWith("getFloors");
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "post";
	}

}
