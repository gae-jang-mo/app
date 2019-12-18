package com.gaejangmo.apiserver.model.userproduct.controller;


import com.gaejangmo.apiserver.commons.logging.EnableLog;
import com.gaejangmo.apiserver.model.common.exception.ApiErrorResponse;
import com.gaejangmo.apiserver.model.common.resolver.LoginUser;
import com.gaejangmo.apiserver.model.common.resolver.SessionUser;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.UserProductService;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductLatestResponseDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@EnableLog
@RestController
@RequestMapping("/api/v1/users/products")
public class UserProductApiController {
    private final UserProductService userProductService;

    public UserProductApiController(final UserProductService userProductService) {
        this.userProductService = userProductService;
    }

    @EnableLog
    @PostMapping
    public ResponseEntity<UserProductResponseDto> create(@RequestBody final UserProductCreateDto userProductCreateDto,
                                                         @LoginUser final SessionUser sessionUser) {
        Long userId = sessionUser.getId();
        UserProductResponseDto responseDto = userProductService.save(userProductCreateDto, userId);
        URI uri = linkTo(UserProductApiController.class).slash(responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserProductResponseDto>> list(@PathVariable final Long userId) {
        List<UserProductResponseDto> responseDtos = userProductService.findByUserId(userId);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<UserProductLatestResponseDto>> latest(@RequestParam(required = false) final Long lastId,
                                                                     @RequestParam(defaultValue = "10") final Integer size) {
        List<UserProductLatestResponseDto> responseDtos = userProductService.findByIdLessThanOrderByIdDesc(lastId, size);
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{id}/comment")
    public ResponseEntity<UserProductResponseDto> updateComment(@PathVariable final Long id,
                                                                @RequestBody final String comment,
                                                                @LoginUser final SessionUser sessionUser) {
        Long userId = sessionUser.getId();
        UserProductResponseDto responseDto = userProductService.updateComment(id, userId, comment);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}/product-type")
    public ResponseEntity<UserProductResponseDto> updateProductType(@PathVariable final Long id,
                                                                    @RequestBody final String productType,
                                                                    @LoginUser final SessionUser sessionUser) {
        Long userId = sessionUser.getId();
        UserProductResponseDto responseDto = userProductService.updateProductType(id, userId, ProductType.ofName(productType));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable final Long id,
                                 @LoginUser final SessionUser sessionUser) {
        Long userId = sessionUser.getId();
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
