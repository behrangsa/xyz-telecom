package org.behrang.telecom.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
public class PhoneNumber {

    private UUID id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID customerId;

    private String phoneNumber;

    private boolean isActive;

}
