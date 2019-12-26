package com.gaejangmo.apiserver.model.userproduct.controller;


import com.gaejangmo.apiserver.commons.logging.EnableLog;
import com.gaejangmo.apiserver.config.oauth.SecurityUser;
import com.gaejangmo.apiserver.model.common.exception.ApiErrorResponse;
import com.gaejangmo.apiserver.model.common.resolver.LoginUser;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.dto.CommentDto;
import com.gaejangmo.apiserver.model.userproduct.dto.ProductTypeDto;
import com.gaejangmo.apiserver.model.userproduct.service.UserProductService;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductExternalRequestDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductInternalRequestDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductLatestResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EnableLog
@RestController
@RequestMapping("/api/v1/users")
public class UserProductApiController {
    private final UserProductService userProductService;

    public UserProductApiController(final UserProductService userProductService) {
        this.userProductService = userProductService;
    }

    @EnableLog
    @PostMapping("/products/internal")
    public ResponseEntity<UserProductResponseDto> createFromInternal(@RequestBody final UserProductInternalRequestDto requestDto,
                                                                     @LoginUser final SecurityUser securityUser) {
        Long userId = securityUser.getId();
        UserProductResponseDto responseDto = userProductService.saveFromInternal(requestDto, userId);
        URI uri = linkTo(UserProductApiController.class).slash(responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @EnableLog
    @PostMapping("/products/external")
    public ResponseEntity<UserProductResponseDto> createFromExternal(@RequestBody final UserProductExternalRequestDto requestDto,
                                                                     @LoginUser final SecurityUser securityUser) {
        Long userId = securityUser.getId();
        UserProductResponseDto responseDto = userProductService.saveFromExternal(requestDto, userId);
        URI uri = linkTo(UserProductApiController.class).slash(responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping("/{username}/products")
    public ResponseEntity<List<UserProductResponseDto>> list(@PathVariable final String username) {
        List<UserProductResponseDto> responseDtos = userProductService.findByUsername(username);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/products/latest")
    public ResponseEntity<List<UserProductLatestResponseDto>> latest(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) final Pageable pageable,
            @LoginUser SecurityUser securityUser) {
        List<UserProductLatestResponseDto> responseDtos = userProductService.findAllByPageable(pageable, securityUser.getId());
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/products/history")
    public ResponseEntity<List<UserProductLatestResponseDto>> history(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) final Pageable pageable,
            @LoginUser SecurityUser securityUser) {
        List<UserProductLatestResponseDto> responseDtos = userProductService.findHistoryByPageable(pageable, securityUser.getId());
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/products/{id}/comment")
    public ResponseEntity<UserProductResponseDto> updateComment(@PathVariable final Long id,
                                                                @RequestBody final CommentDto commentDto,
                                                                @LoginUser final SecurityUser securityUser) {
        Long userId = securityUser.getId();
        UserProductResponseDto responseDto = userProductService.updateComment(id, userId, commentDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/products/{id}/product-type")
    public ResponseEntity<UserProductResponseDto> updateProductType(@PathVariable final Long id,
                                                                    @RequestBody final ProductTypeDto productTypeDto,
                                                                    @LoginUser final SecurityUser securityUser) {
        Long userId = securityUser.getId();
        UserProductResponseDto responseDto = userProductService.updateProductType(id, userId, ProductType.ofName(productTypeDto.getProductType()));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity delete(@PathVariable final Long id,
                                 @LoginUser final SecurityUser securityUser) {
        Long userId = securityUser.getId();
        userProductService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiErrorResponse> handleException(final Exception exception) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(apiErrorResponse);
    }
}
