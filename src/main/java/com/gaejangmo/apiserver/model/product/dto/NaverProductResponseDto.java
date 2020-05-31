package com.gaejangmo.apiserver.model.product.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.gaejangmo.apiserver.model.product.domain.vo.NaverProductType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @JsonGetter("productName")
    public String getProductName() {
        return productName;
    }

    @JsonGetter("buyUrl")
    public String getBuyUrl() {
        return buyUrl;
    }

    @JsonGetter("imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    @JsonGetter("lowestPrice")
    public long getLowestPrice() {
        return lowestPrice;
    }

    @JsonGetter("highestPrice")
    public long getHighestPrice() {
        return highestPrice;
    }

    @JsonGetter("mallName")
    public String getMallName() {
        return mallName;
    }

    @JsonGetter("productId")
    public long getProductId() {
        return productId;
    }

    @JsonGetter("naverProductType")
    public NaverProductType getNaverProductType() {
        return naverProductType;
    }

    @JsonSetter("title")
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @JsonSetter("link")
    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }

    @JsonSetter("image")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonSetter("lprice")
    public void setLowestPrice(long lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    @JsonSetter("hprice")
    public void setHighestPrice(long highestPrice) {
        this.highestPrice = highestPrice;
    }

    @JsonSetter("mallName")
    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    @JsonSetter("productId")
    public void setProductId(long productId) {
        this.productId = productId;
    }

    @JsonSetter("productType")
    public void setNaverProductType(NaverProductType naverProductType) {
        this.naverProductType = naverProductType;
    }
}
