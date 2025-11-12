package com.casa_training.casa_task_day_one.presentation.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] PUBLIC_URLS = {
        "/api/auth/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .authorizeHttpRequests(auth-> auth
                    .requestMatchers(PUBLIC_URLS).permitAll()
                    .anyRequest().permitAll()
            );
        return http.build();
    }
}
