package com.somecompany.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] SWAGGER_WHITELIST = { "/swagger-resources/**", "/swagger-ui.html", "/v2/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.cors()
			.and()
			.authorizeRequests()
				.antMatchers(
					// Index page
					"/",
					// API endpoint
					"/api/postcode",
					// Error page
					"/error",
					// Web jars
					"/webjars/**",
					// Actuator
					"/actuator/**",
					// H2 DB console
					"/h2-console/**")
						.permitAll()
				.antMatchers(SWAGGER_WHITELIST)
					.permitAll()
				.anyRequest()
					.authenticated()
					.and()
					.oauth2ResourceServer()
						.jwt();

		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
	}
}
