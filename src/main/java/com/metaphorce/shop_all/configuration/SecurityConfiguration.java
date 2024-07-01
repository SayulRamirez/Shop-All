package com.metaphorce.shop_all.configuration;

import com.metaphorce.shop_all.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authProvider = authProvider ;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();

                    request.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();

                    request.requestMatchers(HttpMethod.GET, "/api/v1/user/getAll").hasRole("ADMIN");

                    request.requestMatchers(HttpMethod.GET, "api/v1/products").permitAll();

                    request.anyRequest().authenticated();
                })
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
