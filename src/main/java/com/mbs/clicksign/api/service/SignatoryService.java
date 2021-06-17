package com.mbs.clicksign.api.service;

import com.mbs.clicksign.api.controller.AccountController;
import com.mbs.clicksign.api.model.request.SignerDocumentRequest;
import com.mbs.clicksign.api.model.request.SignerRequest;
import com.mbs.clicksign.api.model.response.SignerDocumentResponse;
import com.mbs.clicksign.api.model.response.SignerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class SignatoryService {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Value("${clicksign.url}")
    private String host;

    @Value("${clicksign.url_signatory}")
    private String url_signatory;

    @Value("${clicksign.url_signatory_in_documents}")
    private String url_signatory_in_documents;

    @Value("${clicksign.access_token}")
    private String access_token;

    private RestTemplate restTemplate;

    @Autowired
    public SignatoryService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .build();
    }

    public ResponseEntity<?> getSignatory(String key, String access_token) {
        try {
            String url = host + url_signatory + "/" + key + "?access_token=" + ((Objects.nonNull(access_token) && access_token.length() == 36) ? access_token : this.access_token);
            HttpEntity<String> requestEntity = new HttpEntity<>(key);
            return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            if ((e.getStatusCode()).equals(HttpStatus.NOT_FOUND)) {
                log.info("O Signatário informado não foi encontrado");
                return ResponseEntity.notFound().build();
            }
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<SignerResponse> insertSignatory(SignerRequest request, String access_token) {
        try {
            String url = host + url_signatory + "?access_token=" + ((Objects.nonNull(access_token) && access_token.length() == 36) ? access_token : this.access_token);
            HttpEntity<SignerRequest> requestEntity = new HttpEntity<>(request);
            return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SignerResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> insertSignatoryInDocument(SignerDocumentRequest request, String access_token) {
        try {
            String url = host + url_signatory_in_documents + "?access_token=" + ((Objects.nonNull(access_token) && access_token.length() == 36) ? access_token : this.access_token);
            HttpEntity<SignerDocumentRequest> requestEntity = new HttpEntity<>(request);
            return restTemplate.exchange(url, HttpMethod.POST, requestEntity, SignerDocumentResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return ResponseEntity.badRequest().build();
    }
}
