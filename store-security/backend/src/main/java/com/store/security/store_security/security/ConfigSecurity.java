package com.store.security.store_security.security;

import com.store.security.store_security.exceptionhandle.CustomAccessDeniedHandler;
import com.store.security.store_security.exceptionhandle.CustomAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class ConfigSecurity {

    @Value("${store.security.allowed-origin}")
    private String origin;

    /**
     * Bean responsible for customizable Spring Security configurations
     * and for defining the security filter chain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/article/addArticle",
                                        "/api/article/addArticle/**",
                                        "/api/article/deleteArticle/{id}",
                                        "/api/article/decrementArticle").hasRole("ADMIN")
                                .requestMatchers("/api/user/{username}").hasRole("USER")
                                .requestMatchers("/api/orders").hasAnyRole("ROLE","ADMIN")
                                .requestMatchers("/api/auth/user").authenticated()
                               .requestMatchers("/api/auth/registration",
                                       "/v3/api-docs",
                                       "/h2-console/**",
                                       "/v3/api-docs/**",
                                       "/swagger-ui/**",
                                       "/swagger-ui.html",
                                       "/swagger-ui/index.html"
                                       ).permitAll());

        http.headers(AbstractHttpConfigurer::disable); //H2
        http.cors(cors->cors.configurationSource(
                new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(
                            HttpServletRequest request) {
                        CorsConfiguration cors = new CorsConfiguration();
                        cors.addAllowedOrigin(origin);
                        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH"));
                        cors.setAllowCredentials(true);
                        cors.setAllowedHeaders(List.of("*"));
                        cors.setMaxAge(3600L);
                        return cors;
                    }
                }
        ));

        //autenticazione


        http.formLogin(AbstractHttpConfigurer::disable);
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
