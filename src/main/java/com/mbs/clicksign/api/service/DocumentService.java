package com.mbs.clicksign.api.service;

import com.mbs.clicksign.api.controller.AccountController;
import com.mbs.clicksign.api.model.request.DocumentRequest;
import com.mbs.clicksign.api.model.response.DocumentResponse;
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
public class DocumentService {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Value("${clicksign.url}")
    private String host;

    @Value("${clicksign.url_documents}")
    private String url_document;

    @Value("${clicksign.access_token}")
    private String access_token;

    private RestTemplate restTemplate;

    @Autowired
    public DocumentService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<?> getDocument(String key, String access_token) {
        try {
            String url = host + url_document + "/" + key + "?access_token=" + ((Objects.nonNull(access_token) && access_token.length() == 36) ? access_token : this.access_token);
            return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            if ((e.getStatusCode()).equals(HttpStatus.NOT_FOUND)) {
                log.info("O Documento informado não foi encontrado");
                return ResponseEntity.notFound().build();
            }
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> insertDocument(DocumentRequest request, String access_token) {
        try {
            String url = host + url_document + "?access_token=" + ((Objects.nonNull(access_token) && access_token.length() == 36) ? access_token : this.access_token);
            HttpEntity<DocumentRequest> requestEntity = new HttpEntity<>(request);
            return restTemplate.exchange(url, HttpMethod.POST, requestEntity, DocumentResponse.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> cancelDocument(String key, String access_token) {
        try {
            String url = host + url_document + "/" + key + "/cancel" + "?access_token=" + ((Objects.nonNull(access_token) && access_token.length() == 36) ? access_token : this.access_token);
            String r = restTemplate.patchForObject(url, null, String.class);
            return ResponseEntity.badRequest().build();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return ResponseEntity.badRequest().build();
    }
}
