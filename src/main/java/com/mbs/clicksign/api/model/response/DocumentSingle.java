package com.mbs.clicksign.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Arrays;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentSingle {
    private String key;
    private String path;
    private String filename;
    private String uploaded_at;
    private String updated_at;
    private String finished_at;
    private String deadline_at;
    private String status;
    private boolean auto_close;
    private String locale;
    private Metadata metadata;
    private boolean sequence_enabled;
    private String signable_group;
    private String remind_interval;
    private String content_base64;
    private Downloads downloads;
    private String template;
    private String[] signers;
    private Events[] events;

    @Override
    public String toString() {
        return "DocumentSingle{" +
                "key='" + key + '\'' +
                ", path='" + path + '\'' +
                ", filename='" + filename + '\'' +
                ", uploaded_at='" + uploaded_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", finished_at='" + finished_at + '\'' +
                ", deadline_at='" + deadline_at + '\'' +
                ", status='" + status + '\'' +
                ", auto_close='" + auto_close + '\'' +
                ", locale='" + locale + '\'' +
                ", metadata=" + metadata +
                ", sequence_enabled='" + sequence_enabled + '\'' +
                ", signable_group='" + signable_group + '\'' +
                ", remind_interval='" + remind_interval + '\'' +
                ", content_base64='" + content_base64 + '\'' +
                ", downloads=" + downloads +
                ", template='" + template + '\'' +
                ", signers=" + Arrays.toString(signers) +
                ", events=" + Arrays.toString(events) +
                '}';
    }
}
