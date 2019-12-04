package com.gaejangmo.apiserver.model.product.controller;

import com.gaejangmo.apiserver.model.product.dto.ProductDto;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
public class ProductApiController {
    private final ProductService productService;

    @GetMapping("/api/v1/products")
    public ResponseEntity<ProductDto> find(@RequestParam(name = "productName") String name) {
        ProductDto productDto = productService.findByProductName(name);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/api/v1/products")
    public ResponseEntity<ProductDto> save(@RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.save(productDto);
        // TODO uri 생성 부분이 맞는지
        return ResponseEntity.created(linkTo(ProductApiController.class).toUri()).body(savedProduct);
    }
}
