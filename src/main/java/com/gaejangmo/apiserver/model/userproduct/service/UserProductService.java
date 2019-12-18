package com.gaejangmo.apiserver.model.userproduct.service;

import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.service.UserService;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProduct;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProductRepository;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductLatestResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.exception.NotUserProductOwnerException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserProductService {
    private static final int DEFAULT_PAGE_NUM = 0;

    private final ProductService productService;
    private final UserService userService;
    private final UserProductRepository userProductRepository;

    public UserProductService(final ProductService productService, final UserService userService, final UserProductRepository userProductRepository) {
        this.productService = productService;
        this.userService = userService;
        this.userProductRepository = userProductRepository;
    }

    public UserProductResponseDto save(final UserProductCreateDto userProductCreateDto, final Long userId) {
        User user = userService.findById(userId);
        Product product = productService.findById(userProductCreateDto.getProductId());
        UserProduct userProduct = toEntity(userProductCreateDto, product, user);
        UserProduct saved = userProductRepository.save(userProduct);

        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public UserProduct findById(final Long id) {
        return userProductRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<UserProductResponseDto> findByUserId(final Long userId) {
        User user = userService.findById(userId);
        return userProductRepository.findByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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

    public List<UserProductLatestResponseDto> findAllByPageable(final Pageable pageable) {
        return userProductRepository.findAll(pageable)
                .map(this::toLatestDto).toList();
    }

    private UserProductLatestResponseDto toLatestDto(final UserProduct userProduct) {
        return UserProductLatestResponseDto.builder()
                .id(userProduct.getId())
                .productType(userProduct.getProductType())
                .productImageUrl(userProduct.getProduct().getImageUrl())
                .productName(userProduct.getProduct().getProductName())
                .userImageUrl(userProduct.getUser().getImageUrl())
                .username(userProduct.getUser().getUsername())
                .motto(userProduct.getUser().getMotto())
                .createdAt(userProduct.getCreatedAt())
                .build();
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

    private UserProduct toEntity(final UserProductCreateDto userProductCreateDto, final Product product, final User user) {
        return UserProduct.builder()
                .user(user)
                .product(product)
                .productType(userProductCreateDto.getProductType())
                .comment(Comment.of(userProductCreateDto.getComment()))
                .build();
    }
}
