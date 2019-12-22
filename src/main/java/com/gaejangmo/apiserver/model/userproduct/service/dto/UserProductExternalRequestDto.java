package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductExternalRequestDto {
    private UserProductRequestDto userProductRequestDto;
    private ProductRequestDto productRequestDto;

    @Builder
    public UserProductExternalRequestDto(final UserProductRequestDto userProductRequestDto, final ProductRequestDto productRequestDto) {
        this.userProductRequestDto = userProductRequestDto;
        this.productRequestDto = productRequestDto;
    }
}
