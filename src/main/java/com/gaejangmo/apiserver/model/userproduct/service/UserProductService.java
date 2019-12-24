package com.gaejangmo.apiserver.model.userproduct.service;

import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import com.gaejangmo.apiserver.model.like.service.LikeService;
import com.gaejangmo.apiserver.model.product.domain.Product;
import com.gaejangmo.apiserver.model.product.dto.ManagedProductResponseDto;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import com.gaejangmo.apiserver.model.user.domain.User;
import com.gaejangmo.apiserver.model.user.service.UserService;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProduct;
import com.gaejangmo.apiserver.model.userproduct.domain.UserProductRepository;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.Comment;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.dto.CommentDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.*;
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
    private final ProductService productService;
    private final UserService userService;
    private final UserProductRepository userProductRepository;
    private final LikeService likeService;

    public UserProductService(final ProductService productService, final UserService userService,
                              final UserProductRepository userProductRepository, final LikeService likeService) {
        this.productService = productService;
        this.userService = userService;
        this.userProductRepository = userProductRepository;
        this.likeService = likeService;
    }

    public UserProductResponseDto saveFromInternal(final UserProductInternalRequestDto requestDto, final Long userId) {
        return save(requestDto.getUserProductRequestDto(), userId, requestDto.getProductId());
    }

    public UserProductResponseDto saveFromExternal(final UserProductExternalRequestDto requestDto, final Long userId) {
        ManagedProductResponseDto managedProductResponseDto = productService.save(requestDto.getProductRequestDto());
        return save(requestDto.getUserProductRequestDto(), userId, managedProductResponseDto.getId());
    }

    private UserProductResponseDto save(final UserProductRequestDto requestDto, final Long userId, final Long productId) {
        User user = userService.findById(userId);
        Product product = productService.findById(productId);
        UserProduct userProduct = toEntity(requestDto, product, user);
        UserProduct saved = userProductRepository.save(userProduct);

        return toDto(saved);
    }

    private UserProduct toEntity(final UserProductRequestDto requestDto, final Product product, final User user) {
        return UserProduct.builder()
                .product(product)
                .user(user)
                .comment(Comment.of(requestDto.getComment()))
                .productType(requestDto.getProductType())
                .build();
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

    public UserProductResponseDto updateComment(final Long id, final Long userId, final CommentDto commentDto) {
        return updateTemplate(id, userId, (userProduct) -> toDto(userProduct.changeComment(Comment.of(commentDto.getComment()))));
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

    public List<UserProductLatestResponseDto> findAllByPageable(final Pageable pageable, final SecurityUser loginUser) {
        return userProductRepository.findAll(pageable)
                .map(userProduct -> toLatestDto(userProduct,
                        likeService.isLiked(loginUser, userProduct.getUser().getId())))
                .toList();
    }

    private UserProductLatestResponseDto toLatestDto(final UserProduct userProduct, final boolean isLiked) {
        return UserProductLatestResponseDto.builder()
                .id(userProduct.getId())
                .productType(userProduct.getProductType())
                .productImageUrl(userProduct.getProduct().getImageUrl())
                .productName(userProduct.getProduct().getProductName())
                .userImageUrl(userProduct.getUser().getImageUrl())
                .username(userProduct.getUser().getUsername())
                .motto(userProduct.getUser().getMotto())
                .isLiked(isLiked)
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
}
