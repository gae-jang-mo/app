package com.gaejangmo.apiserver.model.product.controller;

import com.gaejangmo.apiserver.model.product.dto.ProductResponseDto;
import com.gaejangmo.apiserver.model.product.testdata.ProductTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductAcceptanceTest {
    private static final String PRODUCT_API = linkTo(ProductApiController.class).toString();

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient.post()
                .uri(PRODUCT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(Mono.just(ProductTestData.REQUEST_DTO), ProductResponseDto.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void 장비조회() {
        ProductResponseDto productResponseDto = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(PRODUCT_API)
                                .queryParam("productName", "애플 맥북 프로 15형 2019년형 MV912KH/A")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(productResponseDto).isEqualTo(ProductTestData.RESPONSE_DTO);
    }
}
