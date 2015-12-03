package com.exclusively.aggregator.security;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public class EhcacheSecurityContextRepository implements SecurityContextRepository {
	
	RedisTemplate<String, Object> redisTemplate;
	private CacheManager cacheManager;
	private ConcurrentHashMap<String, SecurityContext> mapCache;

	@Override
	public boolean containsContext(HttpServletRequest request) {
		String token = this.getToken(request);
		if(token != null) {
			return (redisTemplate.opsForValue().get(token) != null ? true : false);
//			return mapCache.containsKey(token);
		} else {
			return false;
		}
	}
		
//		return this.contains("SSSC", getToken(request));

	

	private String getToken(HttpServletRequest request) {
		// TODO Auto-generated method stub	
		String token = request.getHeader("X-API-TOKEN");
		if(StringUtils.isEmpty(token)) {
			return checkCookie(request);
		} else {
			return token;
		}
	}

	private static String checkCookie(HttpServletRequest request) {
		if(request.getCookies() != null) {
			for(Cookie cookie: request.getCookies() ) {
				if(cookie.getName().equals("JSESSIONID")) {
					return cookie.getValue();
				}
			}
		}
		
		return null;
	}
	
	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder holder) {
		if (this.containsContext(holder.getRequest())) {
			String token = this.getToken(holder.getRequest());
			return (SecurityContext) redisTemplate.opsForValue().get(token);
//			return (SecurityContext) mapCache.get(token);
		} else
			return SecurityContextHolder.getContext();
	}

	private Cache getCache() {
		return cacheManager.getCache("SSSC");
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest arg1, HttpServletResponse response) {
		Authentication auth = context.getAuthentication();		
		if(auth != null) {
			Object details = auth.getDetails();
			
			if (details instanceof OAuth2AuthenticationDetails) {
				OAuth2AuthenticationDetails oauth = (OAuth2AuthenticationDetails) details;
				String token = oauth.getTokenValue();
				if(token != null) {
//					mapCache.put(token, context);		
					redisTemplate.opsForValue().set(token,context);
////					response.setHeader("X-API-TOKEN", token);
////					Cookie cookie = new Cookie("X-API-TOKEN", token);
////					cookie.setPath("/");
//					cookie.setMaxAge(Integer.MAX_VALUE);;
//					response.addCookie(cookie);
				}
				String jsessionId = checkCookieFromResponse(response) ;
				if(jsessionId != null) {
//					mapCache.put(jsessionId, context);
					redisTemplate.opsForValue().set(jsessionId,context);
				}
			}
		}
	}
	
	private String checkCookieFromResponse(HttpServletResponse response) {
		if(response.getHeader("SET-COOKIE") != null) {
			String header = new String(response.getHeader("SET-COOKIE").getBytes());
			return header.substring(header.indexOf("=") + 1, header.indexOf(";"));
		}
		
		return null;
	}

//	public boolean contains(String cacheName, Object o) {
//		  Ehcache cache = (Ehcache) cacheManager.getCache(cacheName).getNativeCache();
//		  for (Object key: cache.getKeys()) {
//		    Element element = cache.get(key);
//		    System.out.println("Key-- " + key);
//		    if (element != null && element.getObjectValue().equals(o)) {
//		      return true;
//		    }
//		  }
//		  return false;
//		}

}
