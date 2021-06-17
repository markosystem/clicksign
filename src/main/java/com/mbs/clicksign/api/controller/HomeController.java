package com.mbs.clicksign.api.controller;

import com.mbs.clicksign.api.model.request.HomeRequest;
import com.mbs.clicksign.api.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Home")
public class HomeController {

    @Autowired
    private HomeService service;

    @ApiOperation(value = "Função que Cria um Signatário, Documento, realiza vínculo e envia notificação conforme os parâmetros!")
    @RequestMapping(value = "/sendSignature", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendSignature(@RequestBody HomeRequest homeRequest, @RequestParam(required = false) String access_token) {
        ResponseEntity<?> response = service.insertAllClickSign(homeRequest, access_token);
        if (!response.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().body(response.getBody());
        return ResponseEntity.ok(response.getBody());
    }
}
