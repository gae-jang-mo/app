package com.gaejangmo.apiserver.model.product.domain.vo;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
public class Price {
    private long value;

    private Price() {
    }

    private Price(final long value) {
        this.value = value;
    }

    public static Price of(final long value) {
        return new Price(value);
    }

    Price add(final Price other) {
        return new Price(value + other.value);
    }

    Price setPrice(final long value) {
        return new Price(value);
    }

    public long value() {
        return value;
    }
}
