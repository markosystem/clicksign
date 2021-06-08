package com.mbs.clicksign.api.model.response;

import lombok.Data;

@Data
public class DataEvent {
    private User user;
    private Account account;
    private String deadline_at;
    private boolean auto_close;
    private String locale;
}
