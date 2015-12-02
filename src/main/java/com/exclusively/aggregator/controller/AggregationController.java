package com.exclusively.aggregator.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.model.Account;
import com.exclusively.aggregator.services.CatalogAggregatorService;
import com.google.gson.Gson;

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
	
	@Value("${user.api.url}")
	private String userAPIURL;

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
	public @ResponseBody Map<String, Object> goHome(HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Map<String, Object> accountDetails = new HashMap<String, Object>();
		Object details = auth.getDetails();
		if (null != details && details instanceof OAuth2AuthenticationDetails) {
			OAuth2AuthenticationDetails oauth = (OAuth2AuthenticationDetails) details;
			try{
				HttpResponse httpResponse = null;
				CredentialsProvider provider = new BasicCredentialsProvider();
				HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
				httpResponse = httpClient.execute(new HttpGet(userAPIURL+"/uaa/userDetails?access_token="+oauth.getTokenValue()));
				InputStream inputStream = httpResponse.getEntity().getContent();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					Gson gson = new Gson();
					Account account = gson.fromJson(line, Account.class);
					accountDetails.put("userDetails", account);
					accountDetails.put("status", Boolean.TRUE);
					response.setHeader(key, oauth.getTokenValue());
				}
			}catch(Exception exception){
				logger.info("Exception occured : "+exception.getStackTrace());
			}
			
//			Map<String, String> headerMap = Maps.newHashMap();
//			headerMap.put(key, oauth.getTokenValue());
//			
//			if(UserValidator.userTokenMap.get(principal) != null)  {
//				UserValidator.tokenAuthMap.remove(UserValidator.userTokenMap.get(principal));
//				UserValidator.userTokenMap.remove(principal);
//			}
//			
//			//Setting AccessToken- Authentication and UserName - AccessToken
//			UserValidator.tokenAuthMap.put(headerMap.get(key), auth);
//			UserValidator.userTokenMap.put(principal,headerMap.get(key));
//			headerMap.put("customer",new ObjectMapper().writeValueAsString(principal));
//			response.setHeader(key, oauth.getTokenValue());
//			return headerMap;
		}
//		return MapUtils.EMPTY_MAP;
		return accountDetails;
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
