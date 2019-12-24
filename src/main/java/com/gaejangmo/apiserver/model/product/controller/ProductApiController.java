package com.gaejangmo.apiserver.model.product.controller;

import com.gaejangmo.apiserver.model.common.exception.ApiErrorResponse;
import com.gaejangmo.apiserver.model.product.dto.ManagedProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.NaverProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/internal")
    public ResponseEntity<ManagedProductResponseDto> findFromInternalResource(@RequestParam String productName) {
        ManagedProductResponseDto product = productService.findFromInternal(productName);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/external")
    public ResponseEntity<List<NaverProductResponseDto>> findFromExternalResource(@RequestParam String productName) {
        List<NaverProductResponseDto> products = productService.findFromExternal(productName);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ManagedProductResponseDto> save(@RequestBody @Valid ProductRequestDto productRequestDto) {
        // TODO DTO 예외처리
        ManagedProductResponseDto savedProduct = productService.save(productRequestDto);
        return ResponseEntity.created(linkTo(ProductApiController.class)
                .slash(savedProduct.getId()).toUri())
                .body(savedProduct);
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
