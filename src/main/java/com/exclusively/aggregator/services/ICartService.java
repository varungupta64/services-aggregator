package com.exclusively.aggregator.services;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("CART-SERVICE")
public interface ICartService {

	@RequestMapping(value = "/cart/addProduct/id/{id}/isGuest/{isGuest}/productId/{productId}/quantity/{quantity}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String addCartItem(@PathVariable("id") String id, @PathVariable("isGuest") boolean isGuest, @PathVariable("productId") String productId, @PathVariable("quantity") Integer quantity);

	@RequestMapping(value = "/cart/removeProduct/id/{id}/productId/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String removeCartItem(@PathVariable("id") String id, @PathVariable("productId") String productId);

	@RequestMapping(value = "/cart/getCart/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String getCart(@PathVariable("id") String id);

	@RequestMapping(value = "/cart/clearCart/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String clearCart(@PathVariable("id") String id);

	@RequestMapping(value = "/cart/getCartCount/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String getCartCount(@PathVariable("id") String id);

	@RequestMapping(value = "/cart/mergeCarts/guestId/{guestId}/id/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public String mergeCarts(@PathVariable("id") String guestId, @PathVariable("id") String id);

}