package com.gaejangmo.apiserver.model.product.service;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.domain.ProductRepository;
import com.gaejangmo.apiserver.model.product.domain.vo.*;
import com.gaejangmo.apiserver.model.product.dto.ManagedProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.NaverProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final String SEARCH_SERVER_END_POINT = "http://localhost:8081/api/v1/products";

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public ProductService(final ProductRepository productRepository, final RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    public Product findById(final long id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<ManagedProductResponseDto> findFromInternal(final String productName, final Pageable pageable) {
        return productRepository.findProductsByProductName(productName, pageable)
                .stream()
                .map(this::toManagedProductResponseDto)
                .collect(Collectors.toList());
    }

    public List<NaverProductResponseDto> findFromExternal(final String productName) {
        return Arrays.asList(Objects.requireNonNull(
                restTemplate.getForObject(createUrl(productName), NaverProductResponseDto[].class)));
    }

    private String createUrl(String productName) {
        return UriComponentsBuilder.fromHttpUrl(SEARCH_SERVER_END_POINT)
                .queryParam("productName", productName)
                .build(false)                       // 한글이 깨져서 encoded 를 false
                .toString();
    }

    public ManagedProductResponseDto save(final ProductRequestDto dto) {
        // db에 이름으로 먼저 검색해서 있으면 엔티티 반환
        Product product = productRepository.findByProductName(ProductName.of(dto.getTitle()))
                .orElseGet(() -> productRepository.save(toEntity(dto)));

        return toManagedProductResponseDto(product);
    }

    private ManagedProductResponseDto toManagedProductResponseDto(final Product product) {
        return ManagedProductResponseDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .buyUrl(product.getBuyUrl())
                .imageUrl(product.getImageUrl())
                .lowestPrice(product.getLowestPrice())
                .highestPrice(product.getHighestPrice())
                .mallName(product.getMallName())
                .productId(product.getProductId())
                .naverProductType(product.getNaverProductType())
                .build();
    }

    private Product toEntity(final ProductRequestDto dto) {
        return Product.builder()
                .productName(ProductName.of(dto.getTitle()))
                .buyUrl(Link.of(dto.getLink()))
                .imageUrl(Link.of(dto.getImage()))
                .lowestPrice(Price.of(dto.getLowestPrice()))
                .highestPrice(Price.of(dto.getHighestPrice()))
                .mallName(MallName.of(dto.getMallName()))
                .productId(ProductId.of(dto.getProductId()))
                .naverProductType(NaverProductType.valueOf(dto.getNaverProductType()))
                .build();
    }
}
