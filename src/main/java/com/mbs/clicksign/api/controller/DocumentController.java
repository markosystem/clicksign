package com.mbs.clicksign.api.controller;

import com.mbs.clicksign.api.model.request.DocumentRequest;
import com.mbs.clicksign.api.service.DocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Document")
public class DocumentController {

    @Autowired
    private DocumentService service;

    @ApiOperation(value = "Função que consulta um Documento na ClickSign")
    @RequestMapping(value = "/viewDocument", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDocument(@RequestParam String key, @RequestParam(required = false) String access_token) {
        ResponseEntity<?> response = service.getDocument(key, access_token);

        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return ResponseEntity.notFound().build();

        if (response.getStatusCode().is2xxSuccessful())
            return ResponseEntity.ok(response.getBody());

        return ResponseEntity.badRequest().body(response.getBody());
    }

    @ApiOperation(value = "Função que cria um Documento na ClickSign")
    @RequestMapping(value = "/createDocument", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertDocument(@RequestBody DocumentRequest request, @RequestParam(required = false) String access_token) {
        ResponseEntity<?> response = service.insertDocument(request, access_token);
        if (!response.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().body(response.getBody());
        return ResponseEntity.ok(response.getBody());
    }

    @ApiOperation(value = "Função que cancela um Documento na ClickSign")
    @RequestMapping(value = "/cancelDocument", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelDocument(@RequestParam String keyDocumnet, @RequestParam(required = false) String access_token) {
        ResponseEntity<?> response = service.cancelDocument(keyDocumnet, access_token);
        if (!response.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().body(response.getBody());
        return ResponseEntity.ok(response.getBody());
    }

}
