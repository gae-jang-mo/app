package com.gaejangmo.apiserver.model.product.dto;

import com.gaejangmo.apiserver.model.product.domain.vo.NaverProductType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class NaverProductResponseDto {
    private String productName;
    private String buyUrl;
    private String imageUrl;
    private long lowestPrice;
    private long highestPrice;
    private String mallName;
    private long productId;
    private NaverProductType naverProductType;
}
