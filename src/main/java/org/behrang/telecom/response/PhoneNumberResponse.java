package org.behrang.telecom.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.behrang.telecom.entity.PhoneNumber;
import org.springframework.hateoas.RepresentationModel;

import java.util.Collections;
import java.util.List;

public final class PhoneNumberResponse extends RepresentationModel<PhoneNumberResponse> {

    private final PhoneNumber content;

    @JsonCreator
    public PhoneNumberResponse(@JsonProperty("content") PhoneNumber content) {
        this.content = content;
    }

    public PhoneNumber getContent() {
        return content;
    }
}
