package com.gaejangmo.apiserver.model.product.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.EmptyValueException;
import com.gaejangmo.utils.StringUtils;
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

    public static ProductName of(final String name) {
        return new ProductName(name);
    }

    private String validate(final String value) {
        if (StringUtils.isBlank(value)) {
            throw new EmptyValueException("상품 이름이 없습니다");
        }
        return value;
    }

    public String value() {
        return value;
    }
}
