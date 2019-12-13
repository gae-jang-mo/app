package com.gaejangmo.apiserver.model.userproduct.controller;


import com.gaejangmo.apiserver.model.common.resolver.LoginUser;
import com.gaejangmo.apiserver.model.common.resolver.SessionUser;
import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.userproduct.service.UserProductService;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/api/v1/users/products")
public class UserProductApiController {
    private final UserProductService userProductService;

    public UserProductApiController(final UserProductService userProductService) {
        this.userProductService = userProductService;
    }

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
                                                                    @LoginUser SessionUser sessionUser) {
        Long userId = sessionUser.getId();
        UserProductResponseDto responseDto = userProductService.updateProductType(id, userId, ProductType.ofName(productType));
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable final Long id,
                                 @LoginUser SessionUser sessionUser) {
        Long userId = sessionUser.getId();
        userProductService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

}
