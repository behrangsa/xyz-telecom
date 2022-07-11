package org.behrang.telecom.model;

import lombok.Value;

@Value
public class Page<T> {

    T content;

    boolean hasNext;

    boolean hasPrev;

}
