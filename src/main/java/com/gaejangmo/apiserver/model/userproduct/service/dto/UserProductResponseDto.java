package com.gaejangmo.apiserver.model.userproduct.service.dto;

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
    private String productType;
    private String comment;
    private LocalDateTime createdAt;
    private String imageUrl;


}