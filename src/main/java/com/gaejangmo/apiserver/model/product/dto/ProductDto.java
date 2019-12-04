package com.gaejangmo.apiserver.model.product.dto;

import com.gaejangmo.apiserver.model.product.domain.NaverProductType;
import com.gaejangmo.apiserver.model.product.domain.ProductType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ProductDto {
    private String title;
    private String link;
    private String image;
    private long lowestPrice;
    private long highestPrice;
    private String mallName;
    private long productId;
    private NaverProductType naverProductType;
    private ProductType productType;
}