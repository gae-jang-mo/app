package com.gaejangmo.apiserver.model.userproduct.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ProductTypeDto {
    private String productType;

    public ProductTypeDto(final String productType) {
        this.productType = productType;
    }
}

