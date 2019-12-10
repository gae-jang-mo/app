package com.gaejangmo.apiserver.model.userproduct.service;

import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProduct;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProductRepository;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.springframework.stereotype.Service;

// TODO: 2019/12/10 서비스 테스트 추가하기
@Service
public class UserProductService {
    // TODO: 2019/12/10 이너서비스 안해도 될까?
    private final ProductService productService;
    private final UserProductRepository userProductRepository;

    public UserProductService(final UserProductRepository userProductRepository, final ProductService productService) {
        this.userProductRepository = userProductRepository;
        this.productService = productService;
    }

    public UserProductResponseDto save(final UserProductCreateDto userProductCreateDto, final Long userId) {
        // todo userId로 user 조회
        Product product = productService.findById(userProductCreateDto.getProductId());
        UserProduct userProduct = toEntity(userProductCreateDto, product);
        UserProduct saved = userProductRepository.save(userProduct);

        return toDto(saved);
    }

    private UserProductResponseDto toDto(final UserProduct userProduct) {
        return UserProductResponseDto.builder()
                .id(userProduct.getId())
                .comment(userProduct.getComment())
                .createdAt(userProduct.getCreatedAt())
                .productType(userProduct.getProductType())
                .imageUrl(userProduct.getProduct().getImageUrl())
                .productId(userProduct.getProduct().getId())
                .build();
    }

    private UserProduct toEntity(final UserProductCreateDto userProductCreateDto, final Product product) {
        return UserProduct.builder()
                .product(product)
                .productType(userProductCreateDto.getProductType())
                .comment(userProductCreateDto.getComment())
                .build();
    }
}
