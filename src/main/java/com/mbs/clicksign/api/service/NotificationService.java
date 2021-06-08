package com.mbs.clicksign.api.service;

import com.mbs.clicksign.api.controller.AccountController;
import com.mbs.clicksign.api.model.request.NotificationEmailRequest;
import com.mbs.clicksign.api.model.request.NotificationSmsOrWhatsAppRequest;
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
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Value("${clicksign.url}")
    private String host;

    @Value("${clicksign.url_notifications_email}")
    private String url_notifications_email;

    @Value("${clicksign.url_notify_by_sms}")
    private String url_notify_by_sms;

    @Value("${clicksign.url_notify_by_whatsapp}")
    private String url_notify_by_whatsapp;

    @Value("${clicksign.access_token}")
    private String access_token;

    private RestTemplate restTemplate;

    @Autowired
    public NotificationService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .build();
    }

    public boolean notifyEmail(NotificationEmailRequest request) {
        try {
            String url = host + url_notifications_email + "?access_token=" + access_token;
            HttpEntity<NotificationEmailRequest> requestEntity = new HttpEntity<>(request);
            ResponseEntity<String> notificationEmail = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (notificationEmail.getStatusCode().is2xxSuccessful())
                return true;
            log.error("Notificação via E-mail não enviada ao Signatário!");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return false;
    }

    public boolean notifySms(NotificationSmsOrWhatsAppRequest request) {
        try {
            String url = host + url_notify_by_sms + "?access_token=" + access_token;
            HttpEntity<NotificationSmsOrWhatsAppRequest> requestEntity = new HttpEntity<>(request);
            ResponseEntity<String> notificationSms = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (notificationSms.getStatusCode().is2xxSuccessful())
                return true;
            log.error("Notificação via SMS não enviada ao Signatário!");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return false;
    }

    public boolean notifyWhatsApp(NotificationSmsOrWhatsAppRequest request) {
        try {
            String url = host + url_notify_by_whatsapp + "?access_token=" + access_token;
            HttpEntity<NotificationSmsOrWhatsAppRequest> request2 = new HttpEntity<>(request);
            ResponseEntity<String> notificationWhatsApp = restTemplate.exchange(url, HttpMethod.POST, request2, String.class);
            if (notificationWhatsApp.getStatusCode().is2xxSuccessful())
                return true;
            log.error("Notificação via WhatsApp não enviada ao Signatário!");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Ocorreu um Erro na Requisição", e);
        }
        return false;
    }

}
