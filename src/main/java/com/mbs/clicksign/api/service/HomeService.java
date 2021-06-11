package com.mbs.clicksign.api.service;

import com.mbs.clicksign.api.controller.AccountController;
import com.mbs.clicksign.api.controller.SignatoryController;
import com.mbs.clicksign.api.model.request.*;
import com.mbs.clicksign.api.model.response.DocumentResponse;
import com.mbs.clicksign.api.model.response.SignerDocumentResponse;
import com.mbs.clicksign.api.model.response.SignerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class HomeService {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private SignatoryController signatoryService;
    private DocumentService documentService;
    private NotificationService notificationService;

    @Autowired
    public HomeService(SignatoryController signatoryService, DocumentService documentService, NotificationService notificationService) {
        this.signatoryService = signatoryService;
        this.documentService = documentService;
        this.notificationService = notificationService;
    }

    public ResponseEntity<?> insertAllClickSign(HomeRequest homeRequest) {
        String[] auths = homeRequest.getAuths().split(",");
        //create Document
        ResponseEntity<?> documentResponse = this.createDocument(homeRequest);

        //create Signatary and Relations with Document
        for (String auth : auths) {
            this.insertOneClickSign(homeRequest, auth, (DocumentResponse) documentResponse.getBody());
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Ok!"));
    }

    public ResponseEntity<?> insertOneClickSign(HomeRequest homeRequest, String auth, DocumentResponse documentResponse) {
        SignerRequest signerRequest = new SignerRequest();
        SignerSingleRequest signerSingleRequest = new SignerSingleRequest();
        signerSingleRequest.setName(homeRequest.getName());
        signerSingleRequest.setEmail(homeRequest.getEmail());
        signerSingleRequest.setPhone_number(homeRequest.getPhone());
        signerSingleRequest.setAuths(new String[]{auth});
        signerRequest.setSigner(signerSingleRequest);
        signerSingleRequest.setBirthday("1990-11-21");
        signerSingleRequest.setDocumentation("960.863.710-40");
        signerSingleRequest.setHas_documentation(true);
        signerSingleRequest.setSelfie_enabled(false);
        signerSingleRequest.setHandwritten_enabled(false);
        signerSingleRequest.setOfficial_document_enabled(false);

        ResponseEntity<?> responseSignatary = this.signatoryService.insertSignatory(signerRequest);
        if (!responseSignatary.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().build();

        SignerResponse signerResponse = (SignerResponse) responseSignatary.getBody();


        SignerDocumentRequest signerDocumentRequest = new SignerDocumentRequest();
        SignerDocumentSingleRequest signerDocumentSingleRequest = new SignerDocumentSingleRequest();
        signerDocumentSingleRequest.setSign_as("sign");
        signerDocumentSingleRequest.setSigner_key(signerResponse.getSigner().getKey());
        signerDocumentSingleRequest.setDocument_key(documentResponse.getDocument().getKey());
        signerDocumentSingleRequest.setMessage(homeRequest.getMessageEmail());
        signerDocumentRequest.setList(signerDocumentSingleRequest);

        ResponseEntity<?> responseSignataryDocument = this.signatoryService.insertSignatoryInDocument(signerDocumentRequest);

        if (!responseSignataryDocument.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().build();

        SignerDocumentResponse signerDocumentResponse = (SignerDocumentResponse) responseSignataryDocument.getBody();
        String authChosen = signerResponse.getSigner().getAuths()[0];

        if (authChosen.equals("email")) {
            NotificationEmailRequest notificationEmailRequest = new NotificationEmailRequest();
            notificationEmailRequest.setMessage(homeRequest.getMessageEmail());
            notificationEmailRequest.setRequest_signature_key(signerDocumentResponse.getList().getRequest_signature_key());
            notificationEmailRequest.setUrl(signerDocumentResponse.getList().getUrl());
            this.notificationService.notifyEmail(notificationEmailRequest);
            return ResponseEntity.ok(Collections.singletonMap("message", "Documento Registrado e enviado com sucesso via notificação E-mail!"));
        }
        if (authChosen.equals("sms") || authChosen.equals("whatsapp")) {
            NotificationSmsOrWhatsAppRequest notificationSmsOrWhatsAppRequest = new NotificationSmsOrWhatsAppRequest();
            notificationSmsOrWhatsAppRequest.setRequest_signature_key(signerDocumentResponse.getList().getRequest_signature_key());
            if (authChosen.equals("sms"))
                this.notificationService.notifySms(notificationSmsOrWhatsAppRequest);
            else
                this.notificationService.notifyWhatsApp(notificationSmsOrWhatsAppRequest);
            return ResponseEntity.ok(Collections.singletonMap("message", "Documento Registrado e enviado com sucesso via notificação " + (authChosen.equals("sms") ? "SMS!" : "WhatsApp!")));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> createDocument(HomeRequest homeRequest) {
        DocumentRequest documentRequest = new DocumentRequest();
        DocumentSingleRequest documentSingleRequest = new DocumentSingleRequest();
        documentSingleRequest.setPath(homeRequest.getPath());
        documentSingleRequest.setContent_base64(homeRequest.getContent_base64());
        documentSingleRequest.setDeadline_at(homeRequest.getDeadline_at());
        documentSingleRequest.setAuto_close(true);
        documentSingleRequest.setLocale("pt-BR");
        documentSingleRequest.setSequence_enabled(false);
        documentRequest.setDocument(documentSingleRequest);

        ResponseEntity<?> responseDocument = this.documentService.insertDocument(documentRequest);

        if (!responseDocument.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().build();

        return responseDocument;
    }
}