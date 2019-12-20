package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.product.dto.ManagedProductResponseDto;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductInternalRequestDto {
    private long productId;
    private ProductType productType;
    private String comment;

    @Builder
    public UserProductInternalRequestDto(final long productId, final ProductType productType, final String comment) {
        this.productId = productId;
        this.productType = productType;
        this.comment = comment;
    }
}
