package com.mbs.clicksign.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignerResponse {
    private SignerSingle signer;

    @Override
    public String toString() {
        return "SignerRequest{" +
                "signer=" + signer +
                '}';
    }
}
