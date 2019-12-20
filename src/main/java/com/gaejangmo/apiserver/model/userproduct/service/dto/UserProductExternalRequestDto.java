package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductExternalRequestDto {
    private ProductRequestDto productRequestDto;
    private ProductType productType;
    private String comment;

    @Builder
    public UserProductExternalRequestDto(final ProductRequestDto productRequestDto, final ProductType productType, final String comment) {
        this.productRequestDto = productRequestDto;
        this.productType = productType;
        this.comment = comment;
    }
}
