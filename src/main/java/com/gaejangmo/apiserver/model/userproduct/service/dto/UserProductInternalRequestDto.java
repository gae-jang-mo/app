package com.gaejangmo.apiserver.model.userproduct.service.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductInternalRequestDto {
    private UserProductRequestDto userProductRequestDto;
    private long productId;

    @Builder
    public UserProductInternalRequestDto(final UserProductRequestDto userProductRequestDto, final long productId) {
        this.userProductRequestDto = userProductRequestDto;
        this.productId = productId;
    }
}
