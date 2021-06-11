package com.mbs.clicksign.api.model.response;

import lombok.Data;

@Data
public class SignerResponse {
    private SignerSingle signer;

    @Override
    public String toString() {
        return "SignerRequest{" +
                "signer=" + signer +
                '}';
    }
}