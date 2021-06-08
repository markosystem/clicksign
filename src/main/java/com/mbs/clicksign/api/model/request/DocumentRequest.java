package com.mbs.clicksign.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentRequest {
    private DocumentSingleRequest document;

    @Override
    public String toString() {
        return "DocumentRequest{" +
                "document=" + document +
                '}';
    }
}
