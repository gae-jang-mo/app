package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.Status;
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
    private Status status;
    private String comment;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String productName;


    @Builder
    public UserProductResponseDto(final Long id, final Long productId, final String productType, final Status status,
                                  final String comment, final LocalDateTime createdAt, final String imageUrl, final String productName) {
        this.id = id;
        this.productId = productId;
        this.productType = productType;
        this.status = status;
        this.comment = comment;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
        this.productName = productName;
    }
}