/*package com.exclusively.aggregator.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@EnableOAuth2Sso
public class LoginConfigurer extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
<<<<<<< Updated upstream
		// http.logout().and().antMatcher("/**").authorizeRequests()
		// .antMatchers("/index.html", "/home.html", "/", "/login").permitAll()
		// .anyRequest().authenticated().and().csrf().
		// .csrfTokenRepository(csrfTokenRepository()).and()
		// .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);

		http.antMatcher("/**").authorizeRequests().anyRequest().hasAnyRole("AUTHENTICATED_USER", "ANONYMOUS").and()
				// .antMatcher("/cart/**").authorizeRequests().anyRequest()
				// .hasAnyRole("AUTHENTICATED_USER","ANONYMOUS").and()
				// .antMatcher("/cart/user/login/**").authorizeRequests().anyRequest().hasRole("AUTHENTICATED_USER").and()
				// .csrfTokenRepository(csrfTokenRepository()).and()
				// .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)

				.logout().logoutUrl("/cart/logout").permitAll().logoutSuccessUrl("/").and()
				// .rememberMe().rememberMeCookieName("REMEMBER_ME_TOKEN")
				// .and()
=======
//		http.logout().and().antMatcher("/**").authorizeRequests()
//		.antMatchers("/index.html", "/home.html", "/", "/login").permitAll()
//		.anyRequest().authenticated().and().csrf().
//		.csrfTokenRepository(csrfTokenRepository()).and()
//		.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
		http
		.csrf().disable();
		http.antMatcher("/**").authorizeRequests().anyRequest().hasAnyRole("AUTHENTICATED_USER","ANONYMOUS").and()
//		.antMatcher("/cart/**").antMatcher("/address/**").authorizeRequests().anyRequest()
//		.hasAnyRole("AUTHENTICATED_USER","ANONYMOUS").and()
//		.antMatcher("/cart/user/login/**").authorizeRequests().anyRequest().hasRole("AUTHENTICATED_USER").and()
//				.csrfTokenRepository(csrfTokenRepository()).and()
//				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
				
				.logout().logoutUrl("/cart/logout").permitAll()
				.logoutSuccessUrl("/").and()
				//.rememberMe().rememberMeCookieName("REMEMBER_ME_TOKEN")
			//	.and()
>>>>>>> Stashed changes
				.sessionManagement().sessionFixation().migrateSession();
	}

	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (csrf != null) {
					Cookie cookie = new Cookie("XSRF-TOKEN", csrf.getToken());
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
}*/

