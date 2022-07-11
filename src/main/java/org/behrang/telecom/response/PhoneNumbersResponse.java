package org.behrang.telecom.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.behrang.telecom.entity.PhoneNumber;
import org.springframework.hateoas.RepresentationModel;

import java.util.Collections;
import java.util.List;

public final class PhoneNumbersResponse extends RepresentationModel<PhoneNumbersResponse> {

    private final List<PhoneNumber> content;

    @JsonCreator
    public PhoneNumbersResponse(@JsonProperty("content") List<PhoneNumber> content) {
        this.content = content == null ? Collections.emptyList() : List.copyOf(content);
    }

    public List<PhoneNumber> getContent() {
        return List.copyOf(content);
    }
}
