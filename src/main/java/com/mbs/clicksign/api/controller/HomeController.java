package com.mbs.clicksign.api.controller;

import com.mbs.clicksign.api.model.request.HomeRequest;
import com.mbs.clicksign.api.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@Api(value = "Home")
public class HomeController {

    @Autowired
    private HomeService service;

    @ApiOperation(value = "Classe Inical")
    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok(Collections.singletonMap("message", "HellowAPI"));
    }

    @ApiOperation(value = "Função que Cria um Signatário, Documento, realiza vínculo e envia notificação")
    @RequestMapping(value = "/sendSignature", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendSignature(@RequestBody HomeRequest homeRequest) {
        ResponseEntity<?> response = service.insertAllClickSign(homeRequest);
        if (!response.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().body(response.getBody());
        return ResponseEntity.ok(response.getBody());
    }
}
