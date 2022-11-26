package ru.soft.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.soft.security.filter.InitialAuthenticationFilter;
import ru.soft.security.filter.JwtAuthenticationFilter;
import ru.soft.security.filter.JwtManager;
import ru.soft.security.provider.OtpAuthenticationProvider;
import ru.soft.security.provider.UsernamePasswordAuthenticationProvider;
import ru.soft.security.proxy.AuthenticationServerProxy;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationServerProxy authenticationServerProxy;

    @Autowired
    private JwtManager jwtManager;

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(usernamePasswordAuthenticationProvider());
        authenticationManagerBuilder.authenticationProvider(otpAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf().disable();

        http.addFilterAt(initialAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter(), BasicAuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest()
                .authenticated();

        return http.build();
    }

    @Bean
    AuthenticationProvider otpAuthenticationProvider() {
        return new OtpAuthenticationProvider(this.authenticationServerProxy);
    }

    @Bean
    AuthenticationProvider usernamePasswordAuthenticationProvider() {
        return new UsernamePasswordAuthenticationProvider(this.authenticationServerProxy);
    }

    @Bean
    OncePerRequestFilter initialAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new InitialAuthenticationFilter(authenticationManager, this.jwtManager);
    }

    @Bean
    OncePerRequestFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(this.jwtManager);
    }
}
