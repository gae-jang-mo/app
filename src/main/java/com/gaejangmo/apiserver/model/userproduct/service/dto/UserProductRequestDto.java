package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductRequestDto {
    private ProductType productType;
    private String comment;

    @Builder
    public UserProductRequestDto(final ProductType productType, final String comment) {
        this.productType = productType;
        this.comment = comment;
    }
}
