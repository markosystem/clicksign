package com.mbs.clicksign.api.model.response;

import lombok.Data;

@Data
public class Events {
    private String name;
    private DataEvent data;
    private String occurred_at;
}