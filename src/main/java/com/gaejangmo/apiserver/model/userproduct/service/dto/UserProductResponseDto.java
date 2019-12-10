package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class UserProductResponseDto {
    private Long id;
    private Long productId;
    private ProductType productType;
    private String comment;
    private LocalDateTime createdAt;
    private String imageUrl;


}