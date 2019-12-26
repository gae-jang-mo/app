package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Status;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductRequestDto {
    private Status status;
    private ProductType productType;
    private String comment;

    @Builder
    public UserProductRequestDto(final ProductType productType, final Status status, final String comment) {
        this.productType = productType;
        this.status = status;
        this.comment = comment;
    }
}
