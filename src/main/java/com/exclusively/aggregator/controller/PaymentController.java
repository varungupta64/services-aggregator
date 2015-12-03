package com.exclusively.aggregator.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exclusively.aggregator.server.UserValidator;
import com.exclusively.aggregator.utils.HashGenerationVer41;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@RequestMapping(value = "/generateHash/amt/{amt}/pInfo/{pInfo}/fName/{fName}/email/{email}", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody String generateHash(@PathVariable("amt") String amount, @PathVariable("pInfo") String productInfo, @PathVariable("fName") String firstname,
			@PathVariable("email") String email, HttpServletRequest request, HttpServletResponse response) {
		String txnid = UUID.randomUUID().toString().replace("-", "");
		JsonObject hash = new JsonObject();
		Map<String, String> validateUser = UserValidator.validateUser(request, response);
		if (validateUser.containsKey("isGuest") && validateUser.get("isGuest") != null && validateUser.get("isGuest").equalsIgnoreCase("true")) {
			hash = HashGenerationVer41.getHashes(txnid, amount, productInfo, firstname, email, null, null, null, null, null, null, null, null);
			hash.addProperty("SURL", "/success");
			hash.addProperty("FURL", "/failure");
		}
		return hash.toString();
	}

	@RequestMapping(value = "/success", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody String successPayment(HttpServletRequest request, HttpServletResponse response) {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("response", "Order placed successfully");
		return jsonObject.toString();
	}

	@RequestMapping(value = "/failure", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody String failurePayment(HttpServletRequest request, HttpServletResponse response) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("response", "Order failed. Please retry");
		return jsonObject.toString();
	}
}
