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

    public ResponseEntity<?> insertAllClickSign(HomeRequest homeRequest, String access_token) {
        String[] auths = homeRequest.getAuths().split(",");
        //create Document
        ResponseEntity<?> documentResponse = this.createDocument(homeRequest, access_token);
        if (!documentResponse.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Houve um erro e não foi possível criar o Documento!"));

        //create Signatary and Relations with Document and send Notification
        for (String auth : auths) {
            this.insertOneClickSign(homeRequest, auth, (DocumentResponse) documentResponse.getBody(), access_token);
        }

        return ResponseEntity.ok(Collections.singletonMap("message", "Operação realizada com sucesso!"));
    }

    public ResponseEntity<?> insertOneClickSign(HomeRequest homeRequest, String auth, DocumentResponse documentResponse, String access_token) {
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

        ResponseEntity<?> responseSignatary = this.signatoryService.insertSignatory(signerRequest, access_token);
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

        ResponseEntity<?> responseSignataryDocument = this.signatoryService.insertSignatoryInDocument(signerDocumentRequest, access_token);

        if (!responseSignataryDocument.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().build();

        SignerDocumentResponse signerDocumentResponse = (SignerDocumentResponse) responseSignataryDocument.getBody();
        String authChosen = signerResponse.getSigner().getAuths()[0];

        return sendNotification(authChosen, homeRequest, signerDocumentResponse, access_token);
    }

    public ResponseEntity<?> createDocument(HomeRequest homeRequest, String access_token) {
        DocumentRequest documentRequest = new DocumentRequest();
        DocumentSingleRequest documentSingleRequest = new DocumentSingleRequest();
        documentSingleRequest.setPath(homeRequest.getPath());
        documentSingleRequest.setContent_base64(homeRequest.getContent_base64());
        documentSingleRequest.setDeadline_at(homeRequest.getDeadline_at());
        documentSingleRequest.setAuto_close(true);
        documentSingleRequest.setLocale("pt-BR");
        documentSingleRequest.setSequence_enabled(false);
        documentRequest.setDocument(documentSingleRequest);

        ResponseEntity<?> responseDocument = this.documentService.insertDocument(documentRequest, access_token);

        if (!responseDocument.getStatusCode().is2xxSuccessful())
            return ResponseEntity.badRequest().build();

        return responseDocument;
    }

    public ResponseEntity<?> sendNotification(String authChosen, HomeRequest homeRequest, SignerDocumentResponse signerDocumentResponse, String access_token) {
        if (authChosen.equals("email")) {
            NotificationEmailRequest notificationEmailRequest = new NotificationEmailRequest();
            notificationEmailRequest.setMessage(homeRequest.getMessageEmail());
            notificationEmailRequest.setRequest_signature_key(signerDocumentResponse.getList().getRequest_signature_key());
            notificationEmailRequest.setUrl(signerDocumentResponse.getList().getUrl());
            this.notificationService.notifyEmail(notificationEmailRequest, access_token);
            return ResponseEntity.ok(Collections.singletonMap("message", "Documento Registrado e enviado com sucesso via notificação E-mail!"));
        }
        if (authChosen.equals("sms") || authChosen.equals("whatsapp")) {
            NotificationSmsOrWhatsAppRequest notificationSmsOrWhatsAppRequest = new NotificationSmsOrWhatsAppRequest();
            notificationSmsOrWhatsAppRequest.setRequest_signature_key(signerDocumentResponse.getList().getRequest_signature_key());
            if (authChosen.equals("sms"))
                this.notificationService.notifySms(notificationSmsOrWhatsAppRequest, access_token);
            else
                this.notificationService.notifyWhatsApp(notificationSmsOrWhatsAppRequest, access_token);
            return ResponseEntity.ok(Collections.singletonMap("message", "Documento Registrado e enviado com sucesso via notificação " + (authChosen.equals("sms") ? "SMS!" : "WhatsApp!")));
        }
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", "Houve um problema ao utilizar o Serviço, favor verificar o log e tente novamente!"));
    }
}
