package com.mbs.clicksign.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Arrays;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountSingle {
    private String name;
    private String key;
    private String[] admins;

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", admins=" + Arrays.toString(admins) +
                '}';
    }
}
