package com.mbs.clicksign.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Arrays;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignerSingleRequest {
    private String email;
    private String phone_number;
    private String[] auths;
    private String name;
    private String documentation;
    private String birthday;
    private boolean has_documentation;
    private boolean selfie_enabled;
    private boolean handwritten_enabled;
    private boolean official_document_enabled;

    @Override
    public String toString() {
        return "SignerSingleRequest{" +
                "email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", auths=" + Arrays.toString(auths) +
                ", name='" + name + '\'' +
                ", documentation='" + documentation + '\'' +
                ", birthday='" + birthday + '\'' +
                ", has_documentation=" + has_documentation +
                ", selfie_enabled=" + selfie_enabled +
                ", handwritten_enabled=" + handwritten_enabled +
                ", official_document_enabled=" + official_document_enabled +
                '}';
    }
}
