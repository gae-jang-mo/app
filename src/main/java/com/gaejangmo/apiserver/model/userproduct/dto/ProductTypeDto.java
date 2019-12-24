package com.gaejangmo.apiserver.model.userproduct.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class ProductTypeDto {
    private String productType;

    public ProductTypeDto(final String productType) {
        this.productType = productType;
    }
}

