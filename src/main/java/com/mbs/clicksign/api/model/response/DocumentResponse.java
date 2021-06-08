package com.mbs.clicksign.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentResponse {
    private DocumentSingle document;

    @Override
    public String toString() {
        return "Document{" +
                "document=" + document +
                '}';
    }
}
