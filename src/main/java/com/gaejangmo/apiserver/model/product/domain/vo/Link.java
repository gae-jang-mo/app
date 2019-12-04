package com.gaejangmo.apiserver.model.product.domain.vo;

import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
public class Link {

    // TODO 이거 뺴고 regex로 검증하기
    @URL
    private String value;

    private Link() {
    }

    private Link(@URL final String value) {
        this.value = value;
    }

    public static Link of(final String value) {
        return new Link(value);
    }

    public String value() {
        return value;
    }
}
