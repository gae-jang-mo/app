package com.gaejangmo.apiserver.model.product.controller;

import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import com.gaejangmo.apiserver.model.product.dto.ProductResponseDto;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApiController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductResponseDto> find(@RequestParam(name = "productName") String name) {
        ProductResponseDto productResponseDto = productService.findByProductName(name);
        return ResponseEntity.ok(productResponseDto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> save(@RequestBody @Valid ProductRequestDto productRequestDto) {
        // TODO DTO 예외처리
        ProductResponseDto savedProduct = productService.save(productRequestDto);
        return ResponseEntity.created(linkTo(ProductApiController.class)
                .slash(savedProduct.getId()).toUri())
                .body(savedProduct);
    }
}
