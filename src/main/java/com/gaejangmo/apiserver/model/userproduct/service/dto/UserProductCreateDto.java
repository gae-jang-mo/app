package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductCreateDto {
    private String comment;
    private ProductType productType;
    private Long productId;

    @Builder
    public UserProductCreateDto(final String comment, final ProductType productType, final Long productId) {
        this.comment = comment;
        this.productType = productType;
        this.productId = productId;
    }
}
