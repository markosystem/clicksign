package com.mbs.clicksign.api.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentSingleRequest {
    private String path;
    private String content_base64;
    private String deadline_at;
    private boolean auto_close;
    private String locale;
    private boolean sequence_enabled;

    @Override
    public String toString() {
        return "DocumentSingleRequest{" +
                "path='" + path + '\'' +
                ", content_base64='" + content_base64 + '\'' +
                ", deadline_at='" + deadline_at + '\'' +
                ", auto_close=" + auto_close +
                ", locale='" + locale + '\'' +
                ", sequence_enabled=" + sequence_enabled +
                '}';
    }
}
