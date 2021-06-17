package com.mbs.clicksign.api.controller;

import com.mbs.clicksign.api.model.request.SignerDocumentRequest;
import com.mbs.clicksign.api.model.request.SignerRequest;
import com.mbs.clicksign.api.service.SignatoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Signatory")
public class SignatoryController {

    @Autowired
    private SignatoryService service;

    @ApiOperation(value = "Função que visualiza um Signatário na ClickSign")
    @RequestMapping(value = "/viewSignatory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSignatory(@RequestParam String key, @RequestParam(required = false) String access_token) {
        ResponseEntity<?> response = service.getSignatory(key, access_token);

        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return ResponseEntity.notFound().build();

        if (response.getStatusCode().is2xxSuccessful())
            return ResponseEntity.ok(response.getBody());

        return ResponseEntity.badRequest().body(response.getBody());
    }

    @ApiOperation(value = "Função que cria um Signatário na ClickSign")
    @RequestMapping(value = "/createSignatory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertSignatory(@RequestBody SignerRequest request, @RequestParam(required = false) String access_token) {
        ResponseEntity<?> response = service.insertSignatory(request, access_token);
        if (!response.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().body(response.getBody());
        return ResponseEntity.ok(response.getBody());
    }

    @ApiOperation(value = "Função que adiciona um Signatário em um Documento na ClickSign")
    @RequestMapping(value = "/createSignatoryInDocument", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertSignatoryInDocument(@RequestBody SignerDocumentRequest request, @RequestParam(required = false) String access_token) {
        ResponseEntity<?> response = service.insertSignatoryInDocument(request, access_token);
        if (!response.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().body(response.getBody());
        return ResponseEntity.ok(response.getBody());
    }
}
