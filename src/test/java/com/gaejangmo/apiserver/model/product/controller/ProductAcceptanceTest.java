package com.gaejangmo.apiserver.model.product.controller;

import com.gaejangmo.apiserver.model.product.domain.ProductTestData;
import com.gaejangmo.apiserver.model.product.dto.ProductDto;
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

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductAcceptanceTest {

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient.post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(Mono.just(ProductTestData.DTO), ProductDto.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void 장비조회() {
        ProductDto productDto = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/v1/products").queryParam("productName", "애플 맥북 프로 15형 2019년형 MV912KH/A")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(productDto).isEqualTo(ProductTestData.DTO);
    }
}
