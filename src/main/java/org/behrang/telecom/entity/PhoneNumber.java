package org.behrang.telecom.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class PhoneNumber {

    private UUID id;

    private UUID customerId;

    private String phoneNumber;

    private boolean isActive;

}
