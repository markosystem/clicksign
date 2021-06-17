package com.mbs.clicksign.api.controller;

import com.mbs.clicksign.api.model.request.NotificationEmailRequest;
import com.mbs.clicksign.api.model.request.NotificationSmsOrWhatsAppRequest;
import com.mbs.clicksign.api.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@Api(value = "Notification")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @ApiOperation(value = "Função que notifica um Signatário por E-mail via ClickSign")
    @RequestMapping(value = "/notifyEmail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> notifyEmail(@RequestBody NotificationEmailRequest request, @RequestParam(required = false) String access_token) {
        boolean isNotifyValid = service.notifyEmail(request, access_token);
        if (!isNotifyValid)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(Collections.singletonMap("message", "Notificação via E-mail enviada com sucesso!"));
    }

    @ApiOperation(value = "Função que notifica um Signatário por SMS via ClickSign")
    @RequestMapping(value = "/notifySms", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> notifySms(@RequestBody NotificationSmsOrWhatsAppRequest request, @RequestParam(required = false) String access_token) {
        boolean isNotifyValid = service.notifySms(request, access_token);
        if (!isNotifyValid)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(Collections.singletonMap("message", "Notificação via SMS enviada com sucesso!"));
    }


    @ApiOperation(value = "Função que notifica um Signatário por WhatsApp via ClickSign")
    @RequestMapping(value = "/notifyWhatsApp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> notifyWhatsApp(@RequestBody NotificationSmsOrWhatsAppRequest request, @RequestParam(required = false) String access_token) {
        boolean isNotifyValid = service.notifyWhatsApp(request, access_token);
        if (!isNotifyValid)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(Collections.singletonMap("message", "Notificação via WhatsApp enviada com sucesso!"));
    }
}
