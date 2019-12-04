package com.gaejangmo.apiserver.model.product.domain.vo;

import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
public class ProductId {
    private Long value;

    private ProductId() {
    }

    private ProductId(final Long value) {
        this.value = value;
    }

    public static ProductId of(final long id) {
        return new ProductId(id);
    }

    public Long value() {
        return value;
    }
}
