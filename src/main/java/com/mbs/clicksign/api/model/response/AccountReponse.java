package com.mbs.clicksign.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountReponse {
    private AccountSingle account;

    @Override
    public String toString() {
        return "Account{" +
                "account=" + account +
                '}';
    }
}
