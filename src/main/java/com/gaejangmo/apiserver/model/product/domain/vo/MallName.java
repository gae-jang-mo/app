package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.EmptyValueException;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
public class MallName {
    private String value;

    private MallName() {
    }

    private MallName(final String value) {
        this.value = validate(value);
    }

    private String validate(final String value) {
        if (value.isEmpty()) {
            throw new EmptyValueException("판매자 이름이 없습니다");
        }
        return value;
    }

    public static MallName of(final String mallName) {
        return new MallName(mallName);
    }

    public String value() {
        return value;
    }
}
