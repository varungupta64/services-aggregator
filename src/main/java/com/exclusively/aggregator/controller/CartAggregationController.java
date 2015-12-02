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
import com.exclusively.aggregator.services.CartService;
import com.google.common.collect.Maps;

/**
 * Client controller, fetches Cart info from the microservice
 * 
 * @author Anshul Chauhan
 */
@RestController
@RequestMapping("/cart")
public class CartAggregationController {
	@Autowired
	protected CartService cartService;
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
		CartView account = cartService.getCart(validateUser.get(ID));
		return account;
	}

	@RequestMapping(value = "/addProduct/sku/{sku}/quantity/{quantity}", produces = {
			"application/json" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, String> addProductToCart(@PathVariable("sku") String sku,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		boolean account = cartService.addProductToCart(validateUser.get(ID), validateUser.get(IS_GUEST), sku,
				quantity);
		Map<String, String> newHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(1);
		newHashMapWithExpectedSize.put("success", new Boolean(account).toString());
		return newHashMapWithExpectedSize;
	}

	@RequestMapping(value = "/clearCart", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, String> clearCart(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		boolean account = cartService.clearCart(validateUser.get(ID));
		Map<String, String> newHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(1);
		newHashMapWithExpectedSize.put("success", new Boolean(account).toString());
		return newHashMapWithExpectedSize;
	}

	@RequestMapping(value = "/getCartCount", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody Map<String, String> getCartCount(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		String cartCount = cartService.getCartCount(validateUser.get(ID));
		Map<String, String> newHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(1);
		newHashMapWithExpectedSize.put("cartCount", cartCount);
		return newHashMapWithExpectedSize;
	}

	@RequestMapping(value = "/mergeCart", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody String mergeCart(HttpServletRequest request, HttpServletResponse response) {
		// TODO: get both visitorId and emailId somehow and call mergeCat
		throw new NotImplementedException();
	}

}
