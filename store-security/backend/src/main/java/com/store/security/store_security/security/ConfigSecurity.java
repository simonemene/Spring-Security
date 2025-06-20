package com.store.security.store_security.security;

import com.store.security.store_security.exceptionhandle.CustomAccessDeniedHandler;
import com.store.security.store_security.exceptionhandle.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
public class ConfigSecurity {

    /**
     * Bean responsible for customizable Spring Security configurations
     * and for defining the security filter chain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/article/addArticle",
                                        "/api/article/addArticle/**",
                                        "/api/article/deleteArticle/{id}",
                                        "/api/article/decrementArticle").hasRole("ADMIN")
                                .requestMatchers("/api/user/getUserDetails/{username}").hasRole("USER")
                                .requestMatchers("/api/orders").hasAnyRole("ROLE","ADMIN")
                               .requestMatchers("/api/auth/registration", "/h2-console/**").permitAll());

        http.headers(AbstractHttpConfigurer::disable); //H2

        //autenticazione


        http.formLogin(Customizer.withDefaults());
        http.httpBasic(httpbasic->httpbasic.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));
        http.exceptionHandling(exception->exception.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();

    }

    /**
     * Bean responsible for verifying whether a password has been exposed
     * in a known data breach.
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    /**
     * Bean for handling password hashing operations.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
