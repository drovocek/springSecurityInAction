package ru.soft.security.proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationServerProxy {

    private final RestTemplate rest;

    @Value("${auth.server.base.url}")
    private String baseUrl;

    public boolean sendAuth(String username, String password) {
        log.info("sendAuth(username:{} ,password:{}", username, password);
        String url = this.baseUrl + "/user/auth";
        var body = new AuthRequestUser(username, password, null);
        var request = new HttpEntity<>(body);
        var response = this.rest.postForEntity(url, request, Void.class);
        return response.getStatusCode().equals(HttpStatus.OK);
    }

    public boolean sendOTP(String username, String code) {
        log.info("sendOTP(username:{} ,code:{})", username, code);
        String url = this.baseUrl + "/otp/check";
        var body = new AuthRequestUser(username, null, code);
        var request = new HttpEntity<>(body);
        var response = this.rest.postForEntity(url, request, Void.class);
        return response.getStatusCode().equals(HttpStatus.OK);
    }

    record AuthRequestUser(String username, String password, String code) {
    }
}