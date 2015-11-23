package com.exclusively.aggregator.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@EnableOAuth2Sso
public class LoginConfigurer extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
//		http.logout().and().antMatcher("/**").authorizeRequests()
//		.antMatchers("/index.html", "/home.html", "/", "/login").permitAll()
//		.anyRequest().authenticated().and().csrf().
//		.csrfTokenRepository(csrfTokenRepository()).and()
//		.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		
		http.antMatcher("/cart/**").authorizeRequests().anyRequest()
				.hasAnyRole("AUTHENTICATED_USER","ANONYMOUS").and()
//				.csrfTokenRepository(csrfTokenRepository()).and()
//				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
				
				.logout().logoutUrl("/logout").permitAll()
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
}