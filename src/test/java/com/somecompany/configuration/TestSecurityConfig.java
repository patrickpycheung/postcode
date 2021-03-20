package com.somecompany.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("test")
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().authorizeRequests().anyRequest().permitAll();

		http.csrf().ignoringAntMatchers("/api/postcode/**");
		http.headers().frameOptions().sameOrigin();
	}
}
