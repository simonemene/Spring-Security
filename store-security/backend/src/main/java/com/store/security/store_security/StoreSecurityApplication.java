package com.store.security.store_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(jsr250Enabled = true,securedEnabled = true)
public class StoreSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreSecurityApplication.class, args);
	}

}
