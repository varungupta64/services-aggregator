package com.exclusively.aggregator.controller;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.server.UserValidator;
import com.exclusively.aggregator.services.CatalogAggregatorService;
import com.google.common.collect.Maps;

/**
 * Client controller, fetches Order info from the microservice
 * 
 * @author Anshul Chauhan
 */
@RestController
public class AggregationController {

	// @Autowired
	protected CatalogAggregatorService catalogService;

	protected Logger logger = Logger.getLogger(AggregationController.class.getName());
	String key = "X-API-TOKEN";

	/**
	 * this method will be intercepted after successful login and set necessary details in static Map.
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value = "/", produces = { "application/json" })
	public @ResponseBody Map<String, String> goHome(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object details = auth.getDetails();
		if (null != details && details instanceof OAuth2AuthenticationDetails) {
			OAuth2AuthenticationDetails oauth = (OAuth2AuthenticationDetails) details;
			String principal = (String) auth.getPrincipal(); //Will have the user name
			
			Map<String, String> headerMap = Maps.newHashMap();
			headerMap.put(key, oauth.getTokenValue());
			
			if(UserValidator.userTokenMap.get(principal) != null)  {
				UserValidator.tokenAuthMap.remove(UserValidator.userTokenMap.get(principal));
				UserValidator.userTokenMap.remove(principal);
			}
			
			//Setting AccessToken- Authentication and UserName - AccessToken
			UserValidator.tokenAuthMap.put(headerMap.get(key), auth);
			UserValidator.userTokenMap.put(principal,headerMap.get(key));
			headerMap.put("customer",new ObjectMapper().writeValueAsString(principal));
			response.setHeader(key, oauth.getTokenValue());
			return headerMap;
		}
		return MapUtils.EMPTY_MAP;
	}

	@RequestMapping(value = "/category/id/{catId}")
	public @ResponseBody String getByCategoryId(@PathVariable("catId") String catId) {

		logger.info("Catalog service invoked with params: " + catId);

		String account = catalogService.findCategoryByCatId(catId);

		return account;
	}

	@RequestMapping(value = "/category/productlist/{params}")
	public @ResponseBody String getProductList(@PathVariable("params") String params) {

		logger.info("Catalog service invoked with params: " + params);

		String account = catalogService.findProducts(params);

		return account;
	}

}
