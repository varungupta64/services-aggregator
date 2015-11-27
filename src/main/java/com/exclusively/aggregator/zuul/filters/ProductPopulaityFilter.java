package com.exclusively.aggregator.zuul.filters;

import org.springframework.web.bind.annotation.RequestMethod;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ProductPopulaityFilter extends ZuulFilter {

	@Override
	public Object run() {
		long timeStart = System.currentTimeMillis();
		RequestContext context = RequestContext.getCurrentContext();
			try {
				//TODO configure logback
				 log.info(context.getRequest().getRemoteHost() +"," + context.getRequest().getRequestURI().split("/")[5]);
				 System.out.println(context.getRequest().getRemoteHost() +"," + context.getRequest().getRequestURI().split("/")[5]);
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				System.out.println("Time taken by filter " + (System.currentTimeMillis()- timeStart));
			}
			
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return RequestContext.getCurrentContext().getRequest().getRequestURI().contains("product/fetch/id") 
				&& RequestContext.getCurrentContext().getRequest().getMethod().equals(RequestMethod.GET.name())
				&& (RequestContext.getCurrentContext().getResponseStatusCode() == 200);
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
