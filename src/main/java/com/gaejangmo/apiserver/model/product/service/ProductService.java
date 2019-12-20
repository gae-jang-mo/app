package com.gaejangmo.apiserver.model.product.service;

import com.gaejangmo.apiserver.model.common.domain.vo.Link;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.domain.ProductRepository;
import com.gaejangmo.apiserver.model.product.domain.vo.*;
import com.gaejangmo.apiserver.model.product.dto.ManagedProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.NaverProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
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

    public ManagedProductResponseDto findFromInternal(final String productName) {
        Product product = productRepository.findByProductName(ProductName.of(productName));
        if (product == null) {
            throw new EntityNotFoundException("DB에 없는 장비입니다");
        }
        return toDto(product);
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
        Product product = productRepository.save(toEntity(dto));
        return toDto(product);
    }

    private ManagedProductResponseDto toDto(final Product product) {
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
                .naverProductType(NaverProductType.find(1))
                .productType(ProductType.find(1))
                .build();
    }
}
