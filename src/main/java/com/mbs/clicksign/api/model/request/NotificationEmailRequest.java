package com.mbs.clicksign.api.model.request;

import lombok.Data;

@Data
public class NotificationEmailRequest {
    private String request_signature_key;
    private String message;
    private String url;
}
