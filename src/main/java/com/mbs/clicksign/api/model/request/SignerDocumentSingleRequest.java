package com.mbs.clicksign.api.model.request;

import lombok.Data;

@Data
public class SignerDocumentSingleRequest {
    private String document_key;
    private String signer_key;
    private String sign_as;
    private String message;
}
