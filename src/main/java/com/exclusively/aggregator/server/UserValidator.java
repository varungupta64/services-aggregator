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
		Map<String, String> result = new HashMap<>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// If Token is not found in TokenAuthentication Map
		if (auth == null || auth.getName().equals(ANONYMOUS)) {

			String visitorId = request.getHeader("visitorId");
			if (visitorId == null) {
				visitorId = checkCookie(request);
			}
			if (StringUtils.isEmpty(visitorId)) {
				visitorId = UUID.randomUUID().toString();
				response.setHeader("visitorId", visitorId);
				setCookie(response, visitorId);
			}
			result.put(ID, visitorId);
			result.put(IS_GUEST, "true");
		} else {
			result.put(ID, auth.getName());
			result.put(IS_GUEST, "false");
		}
		return result;

	}

	private static void setCookie(HttpServletResponse response, String visitorId) {
		Cookie cookie = new Cookie("visitorId", visitorId);
		cookie.setHttpOnly(false);
		cookie.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookie);

	}

	private static String checkCookie(HttpServletRequest request) {
		if(request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("visitorId")) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
