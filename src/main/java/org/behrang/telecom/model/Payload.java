package org.behrang.telecom.model;

public record Payload<T>(T content, boolean hasNext, boolean hasPrev) {

}
