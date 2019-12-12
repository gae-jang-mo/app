package com.gaejangmo.apiserver.model.userproduct.service;

import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProduct;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProductRepository;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.exception.NotUserProductOwnerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Function;

@Service
@Transactional
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

    @Transactional(readOnly = true)
    public UserProduct findById(final Long id) {
        return userProductRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<UserProductResponseDto> findByUserId(final Long userId) {
        // TODO: 2019/12/11 User로 구현 + 메소드명 수정
        return null;
    }

    public UserProductResponseDto updateComment(final Long id, final Long userId, final String comment) {
        return updateTemplate(id, userId, (userProduct) -> toDto(userProduct.changeComment(Comment.of(comment))));
    }

    public UserProductResponseDto updateProductType(final Long id, final Long userId, final ProductType productType) {
        return updateTemplate(id, userId, (userProduct) -> toDto(userProduct.changeProductType(productType)));
    }

    public boolean delete(final Long id, final Long userId) {
        return updateTemplate(id, userId, UserProduct::delete);
    }

    private <T> T updateTemplate(final Long id, final Long userId, final Function<UserProduct, T> function) {
        UserProduct userProduct = findById(id);
        if (userProduct.matchUser(userId)) {
            return function.apply(userProduct);
        }
        throw new NotUserProductOwnerException();
    }

    private UserProductResponseDto toDto(final UserProduct userProduct) {
        return UserProductResponseDto.builder()
                .id(userProduct.getId())
                .comment(userProduct.getComment())
                .createdAt(userProduct.getCreatedAt())
                .productType(userProduct.getProductType().getName())
                .imageUrl(userProduct.getProduct().getImageUrl())
                .productId(userProduct.getProduct().getId())
                .build();
    }

    private UserProduct toEntity(final UserProductCreateDto userProductCreateDto, final Product product) {
        return UserProduct.builder()
                .product(product)
                .productType(userProductCreateDto.getProductType())
                .comment(Comment.of(userProductCreateDto.getComment()))
                .build();
    }
}
