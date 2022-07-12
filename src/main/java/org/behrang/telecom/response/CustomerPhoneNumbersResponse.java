package org.behrang.telecom.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.behrang.telecom.entity.PhoneNumber;
import org.springframework.hateoas.RepresentationModel;

import java.util.Collections;
import java.util.List;

public final class CustomerPhoneNumbersResponse extends RepresentationModel<CustomerPhoneNumbersResponse> {

    private final List<PhoneNumber> content;

    @JsonCreator
    public CustomerPhoneNumbersResponse(@JsonProperty("content") List<PhoneNumber> content) {
        this.content = content == null ? Collections.emptyList() :
                content.stream().peek(x -> x.setCustomerId(null)).toList();
    }

    public List<PhoneNumber> getContent() {
        return List.copyOf(content);
    }
}
