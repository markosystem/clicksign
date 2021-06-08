package com.mbs.clicksign.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignerRequest {
    private SignerSingleRequest signer;

    @Override
    public String toString() {
        return "SignerRequest{" +
                "signer=" + signer +
                '}';
    }
}
