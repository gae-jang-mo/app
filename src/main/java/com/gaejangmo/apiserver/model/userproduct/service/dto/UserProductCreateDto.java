package com.gaejangmo.apiserver.model.userproduct.service.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductCreateDto {
    private String comment;
    private String productType;
    private Long productId;

    @Builder
    public UserProductCreateDto(final String comment, final String productType, final Long productId) {
        this.comment = comment;
        this.productType = productType;
        this.productId = productId;
    }
}
