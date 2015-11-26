package com.exclusively.aggregator.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.services.AddressAggregatorService;
import com.exclusively.aggregator.entities.Address;

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
		Map<String, String> validateUser = validateUser(request, response);
		return validateUser;
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

	@RequestMapping(value="/deleteAddress/addressId/{addressId}", method=RequestMethod.GET)
	@ResponseBody
	public String deleteAddress(@PathVariable String addressId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = validateUser(request, response);
		if(validateUser.get("IS_GUEST") == "false") {
			return addressService.deleteAddress(addressId);
		}
		else {
			return "User Validation Failed";
		}
	}

	@RequestMapping(value="/saveAddress", method=RequestMethod.POST)
	@ResponseBody
	public String saveAddress(@RequestBody Address address, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = validateUser(request, response);
		if(validateUser.get("IS_GUEST") == "false") {
			return addressService.saveAddress(address);
		}
		else {
			return "User Validation Failed";
		}
	}

	@RequestMapping(value="/getAddress", method=RequestMethod.GET)
	@ResponseBody
	public String getAddress(String email, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = validateUser(request, response);
		if(validateUser.get("IS_GUEST") == "false") {
			return addressService.getAddress(email);
		}
		else {
			return "User Validation Failed";
		}
	}

	@RequestMapping(value="/updateAddress", method=RequestMethod.POST)
	@ResponseBody
	public String updateAddress(@RequestBody Address address, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> validateUser = validateUser(request, response);
		if(validateUser.get("IS_GUEST") == "false") {
			return addressService.updateAddress(address);
		}
		else {
			return "User Validation Failed";
		}
	}

}