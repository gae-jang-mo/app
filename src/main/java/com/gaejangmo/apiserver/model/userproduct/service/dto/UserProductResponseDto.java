package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class UserProductResponseDto {
    private long id;
    private long productId;
    private ProductType productType;
    private String comment;
    private LocalDateTime createdAt;
    // TODO: 2019/12/10 이미지 필드


}