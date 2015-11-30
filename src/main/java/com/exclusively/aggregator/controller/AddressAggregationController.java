package com.exclusively.aggregator.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.entities.Address;
import com.exclusively.aggregator.server.UserValidator;
import com.exclusively.aggregator.services.AddressAggregatorService;

@RestController
@RequestMapping("/address")
public class AddressAggregationController {

	@Autowired
	protected AddressAggregatorService addressService;
	public static String ID = "id";
	public static String IS_GUEST = "isGuest";
	public static String ANONYMOUS = "anonymousUser";

	@RequestMapping("/")
	public Map<String, String> goHome(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		return validateUser;
	}

	@RequestMapping(value = "/deleteAddress/addressId/{addressId}", method = RequestMethod.GET, produces = {
			"application/json" })
	@ResponseBody
	public String deleteAddress(@PathVariable String addressId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		if (validateUser.get("IS_GUEST") == "false") {
			return addressService.deleteAddress(addressId);
		} else {
			return "User Validation Failed";
		}
	}

	@RequestMapping(value = "/saveAddress", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public String saveAddress(@RequestBody Address address, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		if (validateUser.get("IS_GUEST") == "false") {
			return addressService.saveAddress(address);
		} else {
			return "User Validation Failed";
		}
	}

	@RequestMapping(value = "/getAddress", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public String getAddress(String email, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		if (validateUser.get("IS_GUEST") == "false") {
			return addressService.getAddress(email);
		} else {
			return "User Validation Failed";
		}
	}

	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public String updateAddress(@RequestBody Address address, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		if (validateUser.get("IS_GUEST") == "false") {
			return addressService.updateAddress(address);
		} else {
			return "User Validation Failed";
		}
	}

}