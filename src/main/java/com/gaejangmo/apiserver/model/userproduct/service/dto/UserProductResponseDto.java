package com.gaejangmo.apiserver.model.userproduct.service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductResponseDto {
    private Long id;
    private Long productId;
    private String productType;
    private String comment;
    private LocalDateTime createdAt;
    private String imageUrl;


    @Builder
    public UserProductResponseDto(final Long id, final Long productId, final String productType, final String comment, final LocalDateTime createdAt, final String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.productType = productType;
        this.comment = comment;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }
}