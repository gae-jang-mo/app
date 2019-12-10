package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.product.domain.vo.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@ToString
public class UserProductCreateDto {
    private String comment;
    private MultipartFile thumbnail;
    private ProductType productType;
    private long productId;

    @Builder
    public UserProductCreateDto(final String comment, final MultipartFile thumbnail, final ProductType productType, final long productId) {
        this.comment = comment;
        this.thumbnail = thumbnail;
        this.productType = productType;
        this.productId = productId;
    }
}
