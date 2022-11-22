package ru.soft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebAuthorizationConfig {

    @Bean
    SecurityFilterChain httpFilter(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.csrf().disable();

        http.authorizeRequests()
                .mvcMatchers("/email/{email:.*(.+@.+\\.com)}").permitAll()
                .anyRequest().denyAll();

        return http.build();
    }
}

