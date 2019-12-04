package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.EmptyValueException;
import lombok.EqualsAndHashCode;

import javax.persistence.Embeddable;

@Embeddable
@EqualsAndHashCode
public class ProductName {
    private String value;

    private ProductName() {
    }

    private ProductName(final String value) {
        this.value = validate(value);
    }

    private String validate(final String value) {
        if (value.isEmpty()) {
            throw new EmptyValueException("상품 이름이 없습니다");
        }
        return value;
    }

    public static ProductName of(final String name) {
        return new ProductName(name);
    }

    public String value() {
        return value;
    }
}
