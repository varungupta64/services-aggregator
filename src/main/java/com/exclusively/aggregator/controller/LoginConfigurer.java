package com.exclusively.aggregator.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class LoginConfigurer extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.logout().and().antMatcher("/**").authorizeRequests()
//		.antMatchers("/index.html", "/home.html", "/", "/login").permitAll()
//		.anyRequest().authenticated().and().csrf().
//		.csrfTokenRepository(csrfTokenRepository()).and()
//		.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		
		http.antMatcher("/**").authorizeRequests().anyRequest().anonymous().antMatchers("/cart/**")
		.hasAnyRole("AUTHENTICATED_USER","ANONYMOUS").and()
		.antMatcher("/cart/user/login/**").authorizeRequests().anyRequest().hasRole("AUTHENTICATED_USER").and()
//				.csrfTokenRepository(csrfTokenRepository()).and()
//				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
				
				.logout().logoutUrl("/cart/logout").permitAll()
				.logoutSuccessUrl("/").and()
				//.rememberMe().rememberMeCookieName("REMEMBER_ME_TOKEN")
			//	.and()
				.sessionManagement().sessionFixation().migrateSession();
	}

	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request
						.getAttribute(CsrfToken.class.getName());
				if (csrf != null) {
					Cookie cookie = new Cookie("XSRF-TOKEN",
							csrf.getToken());
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	@Controller
	public static class LoginErrors {

	

	}
}