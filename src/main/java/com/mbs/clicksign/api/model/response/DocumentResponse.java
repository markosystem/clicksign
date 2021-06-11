package com.mbs.clicksign.api.model.response;

import lombok.Data;

@Data
public class DocumentResponse {
    private DocumentSingle document;

    @Override
    public String toString() {
        return "Document{" +
                "document=" + document +
                '}';
    }
}