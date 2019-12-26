package com.gaejangmo.apiserver.model.product.domain;

import com.gaejangmo.apiserver.model.common.domain.BaseTimeEntity;
import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.product.domain.vo.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "productName", unique = true, nullable = false))
    private ProductName productName;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "buyUrl", nullable = false))
    private Link buyUrl;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "imageUrl", nullable = false))
    private Link imageUrl;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "lowestPrice", nullable = false))
    private Price lowestPrice;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "highestPrice", nullable = false))
    private Price highestPrice;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "mallName", nullable = false))
    private MallName mallName;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "productId", nullable = false))
    private ProductId productId;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "naverProductType", nullable = false))
    private NaverProductType naverProductType;

    @AttributeOverride(
            name = "value",
            column = @Column(name = "productType"))
    private ProductType productType;

    @Builder
    public Product(final ProductName productName, final Link buyUrl, final Link imageUrl,
                   final Price lowestPrice, final Price highestPrice, final MallName mallName,
                   final ProductId productId, final NaverProductType naverProductType, final ProductType productType) {
        this.productName = productName;
        this.buyUrl = buyUrl;
        this.imageUrl = imageUrl;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
        this.mallName = mallName;
        this.productId = productId;
        this.naverProductType = naverProductType;
        this.productType = productType;
    }

    public String getProductName() {
        return productName.value();
    }

    public String getBuyUrl() {
        return buyUrl.value();
    }

    public String getImageUrl() {
        return imageUrl.value();
    }

    public long getLowestPrice() {
        return lowestPrice.value();
    }

    public long getHighestPrice() {
        return highestPrice.value();
    }

    public String getMallName() {
        return mallName.value();
    }

    public long getProductId() {
        return productId.value();
    }

    public NaverProductType getNaverProductType() {
        return naverProductType;
    }

    public ProductType getProductType() {
        return productType;
    }
}
