package com.mbs.clicksign.api.service;

import com.mbs.clicksign.api.controller.AccountController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Value("${clicksign.url}")
    private String host;

    @Value("${clicksign.url_account}")
    private String url_account;

    private RestTemplate restTemplate;

    @Autowired
    public AccountService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .build();
    }

    public ResponseEntity<?> getAccount(String access_token) {
        try {
            String url = host + url_account + "?access_token=" + access_token;
            HttpEntity<String> requestEntity = new HttpEntity<>(access_token);
            return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return ResponseEntity.badRequest().build();
    }
}
