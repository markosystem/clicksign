package com.mbs.clicksign.api.model.response;

import lombok.Data;

import java.util.Arrays;

@Data
public class SignerSingle {
    private String key;
    private String email;
    private String[] auths;
    private String name;
    private String documentation;
    private String birthday;
    private String phone_number;
    private String has_documentation;
    private String selfie_enabled;
    private String handwritten_enabled;
    private String official_document_enabled;
    private String created_at;
    private String updated_at;

    @Override
    public String toString() {
        return "SignerSingle{" +
                "key='" + key + '\'' +
                ", email='" + email + '\'' +
                ", auths=" + Arrays.toString(auths) +
                ", name='" + name + '\'' +
                ", documentation='" + documentation + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", has_documentation='" + has_documentation + '\'' +
                ", selfie_enabled='" + selfie_enabled + '\'' +
                ", handwritten_enabled='" + handwritten_enabled + '\'' +
                ", official_document_enabled='" + official_document_enabled + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}