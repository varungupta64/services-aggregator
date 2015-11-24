package com.exclusively.aggregator.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.entities.CartView;
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

	@RequestMapping("/")
	public Map<String, String> goHome(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = validateUser(request, response);
		return validateUser;
	}
	@RequestMapping(value = "/cart/getCart")
	public @ResponseBody CartView getCart(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = validateUser(request, response);
		CartView account = catalogService.getCart(validateUser.get(ID));
		return account;
	}

	@RequestMapping(value = "/cart/addProduct/{sku}/quantity/{quantity}")
	public @ResponseBody String addProductToCart(@PathVariable("sku") String sku,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = validateUser(request, response);
		String account = catalogService.addProductToCart(validateUser.get(ID), validateUser.get(IS_GUEST), sku,
				quantity);

		return account;
	}

	public Map<String, String> validateUser(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		Map<String, String> result = new HashMap<>();
		if (name.equals("anonymousUser")) {
			String visitorId = null;
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("visitorId")) {
					visitorId = cookie.getValue();
				}
			}
			if (StringUtils.isEmpty(visitorId)) {
				visitorId = UUID.randomUUID().toString();
				Cookie cookie = new Cookie("visitorId", visitorId);
				cookie.setHttpOnly(false);
				cookie.setMaxAge(Integer.MAX_VALUE);
				response.addCookie(cookie);
				result.put(ID, visitorId);
			} else {
				result.put(ID, visitorId);
			}
			result.put(IS_GUEST, "true");
		} else {
			result.put(ID, name);
			result.put(IS_GUEST, "false");
		}
		return result;
	}

	@RequestMapping(value = "/cart/clearCart")
	public @ResponseBody CartView clearCart(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = validateUser(request, response);
		CartView account = catalogService.clearCart(validateUser.get(ID));
		return account;
	}
}
