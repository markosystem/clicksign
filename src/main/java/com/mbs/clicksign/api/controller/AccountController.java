package com.mbs.clicksign.api.controller;

import com.mbs.clicksign.api.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Api(value = "Account")
public class AccountController {

    @Autowired
    private AccountService service;

    @ApiOperation(value = "Função que verifica a chave Privada da ClickSign")
    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccount(@RequestParam String access_token) {
        ResponseEntity<?> response = service.getAccount(access_token);
        if (!response.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Chave inválida!"));
        return ResponseEntity.ok(response.getBody());
    }
}
