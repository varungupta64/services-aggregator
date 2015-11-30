package com.exclusively.aggregator.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.entities.CartView;
import com.exclusively.aggregator.server.UserValidator;
import com.exclusively.aggregator.services.CartAggregatorService;

/**
 * Client controller, fetches Cart info from the microservice
 * 
 * @author Anshul Chauhan
 */
@RestController
@RequestMapping("/cart")
public class CartAggregationController {
	@Autowired
	protected CartAggregatorService catalogService;
	public static String ID = "id";
	public static String IS_GUEST = "isGuest";
	public static String ANONYMOUS = "anonymousUser";

	@RequestMapping(value = "/index", produces = { "application/json" }, method = RequestMethod.GET)
	public Authentication goHome(HttpServletRequest request, HttpServletResponse response) {
		// Map<String, String> validateUser = validateUser(request, response);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}

	@RequestMapping(value = "/getCart", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody CartView getCart(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		CartView account = catalogService.getCart(validateUser.get(ID));
		return account;
	}

	@RequestMapping(value = "/addProduct/sku/{sku}/quantity/{quantity}", produces = {
			"application/json" }, method = RequestMethod.GET)
	public @ResponseBody boolean addProductToCart(@PathVariable("sku") String sku,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		boolean account = catalogService.addProductToCart(validateUser.get(ID), validateUser.get(IS_GUEST), sku,
				quantity);
		return account;
	}

	@RequestMapping(value = "/clearCart", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody boolean clearCart(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		boolean account = catalogService.clearCart(validateUser.get(ID));
		return account;
	}

	@RequestMapping(value = "/getCartCount", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody String getCartCount(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		return catalogService.getCartCount(validateUser.get(ID));
	}

	@RequestMapping(value = "/mergeCart", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody String mergeCart(HttpServletRequest request, HttpServletResponse response) {
		// TODO: get both visitorId and emailId somehow and call mergeCat
		throw new NotImplementedException();
	}

}
