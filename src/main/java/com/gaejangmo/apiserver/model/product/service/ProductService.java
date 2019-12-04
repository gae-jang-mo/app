package com.gaejangmo.apiserver.model.product.service;

import com.gaejangmo.apiserver.model.product.domain.NaverProductType;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.domain.ProductRepository;
import com.gaejangmo.apiserver.model.product.domain.ProductType;
import com.gaejangmo.apiserver.model.product.domain.vo.*;
import com.gaejangmo.apiserver.model.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto findByProductName(String name) {
        Product product = productRepository.findByProductName(ProductName.of(name));
        return toDto(product);
    }

    public ProductDto save(final ProductDto productDto) {
        Product product = productRepository.save(toEntity(productDto));
        return toDto(product);
    }

    private ProductDto toDto(final Product product) {
        return ProductDto.builder()
                .title(product.getProductName())
                .link(product.getBuyUrl())
                .image(product.getImageUrl())
                .lowestPrice(product.getLowestPrice())
                .highestPrice(product.getHighestPrice())
                .mallName(product.getMallName())
                .productId(product.getProductId())
                .naverProductType(product.getNaverProductType())
                .productType(product.getProductType())
                .build();
    }

    private Product toEntity(final ProductDto productDto) {
        return Product.builder()
                .productName(ProductName.of(productDto.getTitle()))
                .buyUrl(Link.of(productDto.getLink()))
                .imageUrl(Link.of(productDto.getImage()))
                .lowestPrice(Price.of(productDto.getLowestPrice()))
                .highestPrice(Price.of(productDto.getHighestPrice()))
                .mallName(MallName.of(productDto.getMallName()))
                .productId(ProductId.of(productDto.getProductId()))
                .naverProductType(NaverProductType.find(1))
                .productType(ProductType.find(1))
                .build();
    }
}
