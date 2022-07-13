package org.behrang.telecom.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public final class PhoneActivationResponse extends RepresentationModel<PhoneActivationResponse> {

    public static final String STATUS_ACTIVATED = "ACTIVATED";

    private final String content;

    @JsonCreator
    public PhoneActivationResponse(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
