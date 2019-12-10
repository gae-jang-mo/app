package com.gaejangmo.apiserver.model.userproduct.controller;


import com.gaejangmo.apiserver.model.userproduct.service.UserProductService;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductCreateDto;
import com.gaejangmo.apiserver.model.userproduct.service.dto.UserProductResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/api/v1/userproducts")
public class UserProductApiController {
    private final UserProductService userProductService;

    public UserProductApiController(final UserProductService userProductService) {
        this.userProductService = userProductService;
    }

    @PostMapping
    public ResponseEntity<UserProductResponseDto> create(@RequestBody final UserProductCreateDto userProductCreateDto) {
        // TODO: 2019/12/10 유저 정보 가져와서 id 넘기기
        UserProductResponseDto responseDto = userProductService.save(userProductCreateDto, 1L);
        URI uri = linkTo(UserProductApiController.class).slash(responseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDto);
    }
}
