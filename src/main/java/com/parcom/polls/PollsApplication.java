package com.parcom.polls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@EnableAsync
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class PollsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollsApplication.class, args);
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
	}

}

