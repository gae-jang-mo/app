package com.gaejangmo.apiserver.model.product.controller;

import com.gaejangmo.apiserver.model.common.exception.ApiErrorResponse;
import com.gaejangmo.apiserver.model.product.domain.vo.ProductType;
import com.gaejangmo.apiserver.model.product.dto.ManagedProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.NaverProductResponseDto;
import com.gaejangmo.apiserver.model.product.dto.ProductRequestDto;
import com.gaejangmo.apiserver.model.product.exception.InvalidProductRequestException;
import com.gaejangmo.apiserver.model.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApiController {
    private final ProductService productService;

    @GetMapping("/internal")
    public ResponseEntity<List<ManagedProductResponseDto>> findFromInternalResource(@RequestParam String productName,
                                                                                    @PageableDefault Pageable pageable) {
        List<ManagedProductResponseDto> products = productService.findFromInternal(productName, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/external")
    public ResponseEntity<List<NaverProductResponseDto>> findFromExternalResource(@RequestParam String productName) {
        List<NaverProductResponseDto> products = productService.findFromExternal(productName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/types")
    public ResponseEntity<Map<ProductType, String>> showProductTypes() {
        Map<ProductType, String> productNamesWithValues = new EnumMap<>(ProductType.class);
        Arrays.asList(ProductType.values())
                .forEach(x -> productNamesWithValues.put(x, x.getName()));
        return ResponseEntity.ok(productNamesWithValues);
    }

    @PostMapping
    public ResponseEntity<ManagedProductResponseDto> save(@RequestBody @Valid ProductRequestDto productRequestDto, BindingResult bindingResult) {
        validateRequest(bindingResult);
        ManagedProductResponseDto savedProduct = productService.save(productRequestDto);
        return ResponseEntity.created(linkTo(ProductApiController.class)
                .slash(savedProduct.getId()).toUri())
                .body(savedProduct);
    }

    private void validateRequest(final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidProductRequestException("Product request 인자가 잘못되었습니다");
        }
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
