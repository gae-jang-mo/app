package com.gaejangmo.apiserver.model.product.service;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.domain.ProductRepository;
import com.gaejangmo.apiserver.model.product.domain.vo.*;
import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import com.gaejangmo.apiserver.model.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public List<ProductResponseDto> findByProductName(final String productName) {
        List<Product> products = productRepository.findByProductName(ProductName.of(productName));
        if (products.isEmpty()) {
            throw new EntityNotFoundException("DB에 없는 장비입니다");
        }
        return products.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<ProductResponseDto> invokeByProductName(final String productName) {
        return Arrays.asList(Objects.requireNonNull(
                restTemplate.getForObject(getUrl("http://localhost:8081/api/v1/search", productName),
                        ProductResponseDto[].class)));
    }

    private String getUrl(String url, String productName) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("productName", productName)
                .build(false)                       // 한글이 깨져서 encoded 를 false
                .toString();
    }

    public ProductResponseDto save(final ProductRequestDto dto) {
        Product product = productRepository.save(toEntity(dto));
        return toDto(product);
    }

    private ProductResponseDto toDto(final Product product) {
        return ProductResponseDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .buyUrl(product.getBuyUrl())
                .imageUrl(product.getImageUrl())
                .lowestPrice(product.getLowestPrice())
                .highestPrice(product.getHighestPrice())
                .mallName(product.getMallName())
                .productId(product.getProductId())
                .naverProductType(product.getNaverProductType())
                .productType(product.getProductType())
                .build();
    }

    private Product toEntity(final ProductRequestDto dto) {
        return Product.builder()
                .productName(ProductName.of(dto.getTitle()))
                .buyUrl(Link.of(dto.getLink()))
                .imageUrl(Link.of(dto.getImage()))
                .lowestPrice(Price.of(dto.getLPrice()))
                .highestPrice(Price.of(dto.getHPrice()))
                .mallName(MallName.of(dto.getMallName()))
                .productId(ProductId.of(dto.getProductId()))
                .naverProductType(NaverProductType.find(1))
                .productType(ProductType.find(1))
                .build();
    }
}
