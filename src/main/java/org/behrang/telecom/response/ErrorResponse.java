package org.behrang.telecom.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public final class ErrorResponse extends RepresentationModel<ErrorResponse> {

    private final String content;

    @JsonCreator
    public ErrorResponse(@JsonProperty("content") String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
