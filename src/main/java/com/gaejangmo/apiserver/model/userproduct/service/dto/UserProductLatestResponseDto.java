package com.gaejangmo.apiserver.model.userproduct.service.dto;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class UserProductLatestResponseDto {
    private Long id;
    private Map<String, Object> product = new HashMap<>();
    private Map<String, Object> user = new HashMap<>();
    private LocalDateTime createdAt;

    @Builder
    public UserProductLatestResponseDto(final Long id, final ProductType productType, final Status status,
                                        final String productImageUrl, final String productName, final String userImageUrl,
                                        final String username, final String motto, final boolean isLiked, final LocalDateTime createdAt) {
        this.id = id;
        this.product.put("type", productType);
        this.product.put("status", status);
        this.product.put("imageUrl", productImageUrl);
        this.product.put("name", productName);
        this.user.put("imageUrl", userImageUrl);
        this.user.put("username", username);
        this.user.put("motto", motto);
        this.user.put("isLiked", isLiked);
        this.createdAt = createdAt;
    }
}
