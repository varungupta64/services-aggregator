package com.exclusively.aggregator.server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserValidator {
	public static String ID = "id";
	public static String IS_GUEST = "isGuest";
	public static String ANONYMOUS = "anonymousUser";
	public static final Map<String, Authentication> tokenAuthMap = new HashMap<String, Authentication>();
	public static final Map<String, String> userTokenMap = new HashMap<String, String>();

	public static Map<String, String> validateUser(HttpServletRequest request, HttpServletResponse response) {
		String header = request.getHeader("token");
		Authentication auth = null;
		
		if (header != null) {
			auth = UserValidator.tokenAuthMap.get(header);
			//If Token is found in Token Authentication Map
			if (auth != null) {
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		//If Token is not found in TokenAuthentication Map
		if (auth == null) {
			auth = SecurityContextHolder.getContext().getAuthentication();
		}
		
		String name = auth.getName();
		Map<String, String> result = new HashMap<>();
		if (name.equals("anonymousUser")) {
			String visitorId = request.getHeader("visitorId");
			if (StringUtils.isNotEmpty(visitorId)) {
					visitorId = request.getHeader("visitorId");
			}else {
				visitorId = UUID.randomUUID().toString();
				response.setHeader("visitorId", visitorId);
			}
			result.put(ID, visitorId);
			result.put(IS_GUEST, "true");
		} else {
			result.put(ID, name);
			result.put(IS_GUEST, "false");
		}
		return result;
	}
}
