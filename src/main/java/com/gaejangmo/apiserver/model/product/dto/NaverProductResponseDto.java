package com.gaejangmo.apiserver.model.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gaejangmo.apiserver.model.product.domain.vo.NaverProductType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class NaverProductResponseDto {
    @JsonProperty("title")
    private String productName;

    @JsonProperty("link")
    private String buyUrl;

    @JsonProperty("image")
    private String imageUrl;

    @JsonProperty("lprice")
    private long lowestPrice;

    @JsonProperty("hprice")
    private long highestPrice;

    @JsonProperty("mallName")
    private String mallName;

    @JsonProperty("productId")
    private long productId;

    @JsonProperty("productType")
    private NaverProductType naverProductType;
}
