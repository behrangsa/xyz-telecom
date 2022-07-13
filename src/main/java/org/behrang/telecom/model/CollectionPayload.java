package org.behrang.telecom.model;

public record CollectionPayload<T>(T content, boolean hasNext, boolean hasPrev) {

}
