package com.mbs.clicksign.api.model.response;

import lombok.Data;

@Data
public class SignerDocumentSingleResponse {
    private String key;
    private String request_signature_key;
    private String document_key;
    private String signer_key;
    private String sign_as;
    private String created_at;
    private String updated_at;
    private String url;
    private Integer group;
    private String message;

    @Override
    public String toString() {
        return "SignerDocumentSingleResponse{" +
                "key='" + key + '\'' +
                ", request_signature_key='" + request_signature_key + '\'' +
                ", document_key='" + document_key + '\'' +
                ", signer_key='" + signer_key + '\'' +
                ", sign_as='" + sign_as + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", url='" + url + '\'' +
                ", group=" + group +
                ", message='" + message + '\'' +
                '}';
    }
}
