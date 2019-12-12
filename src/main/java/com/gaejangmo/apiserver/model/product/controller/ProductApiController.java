package com.gaejangmo.apiserver.model.product.controller;

import com.gaejangmo.apiserver.model.product.dto.ManagedProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import com.gaejangmo.apiserver.model.product.dto.ProductResponseDto;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApiController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> find(@RequestParam(name = "productName") String productName, @RequestParam(name = "external") boolean external) {
        List<ProductResponseDto> productResponses =
                external ? invokeSearchApi(productName) : findProductResponses(productName);
        return ResponseEntity.ok(productResponses);
    }

    private List<ProductResponseDto> findProductResponses(final String productName) {
        return productService.findByProductName(productName);
    }

    private List<ProductResponseDto> invokeSearchApi(final String productName) {
        return productService.invokeByProductName(productName);
    }

    @PostMapping
    public ResponseEntity<ManagedProductResponseDto> save(@RequestBody @Valid ProductRequestDto productRequestDto) {
        // TODO DTO 예외처리
        ManagedProductResponseDto savedProduct = productService.save(productRequestDto);
        return ResponseEntity.created(linkTo(ProductApiController.class)
                .slash(savedProduct.getId()).toUri())
                .body(savedProduct);
    }
}
