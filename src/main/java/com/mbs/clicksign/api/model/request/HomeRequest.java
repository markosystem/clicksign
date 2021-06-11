package com.mbs.clicksign.api.model.request;

import lombok.Data;

@Data
public class HomeRequest {
    private String name;
    private String email;
    private String phone;
    private String auths;
    private String path;
    private String content_base64;
    private String deadline_at;
    private String messageEmail;
}
