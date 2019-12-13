package com.gaejangmo.apiserver.model.product.controller;

import com.gaejangmo.apiserver.model.product.dto.ProductResponseDto;
import com.gaejangmo.apiserver.model.product.testdata.ProductTestData;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;


@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ProductAcceptanceTest {
    private static final String PRODUCT_API = linkTo(ProductApiController.class).toString();
    private static final String LOCALHOST = "http://localhost:";

    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private String port;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl(LOCALHOST + port)
                .filter(documentationConfiguration(restDocumentation))
                .build();

        webTestClient.post()
                .uri(PRODUCT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .body(Mono.just(ProductTestData.REQUEST_DTO), ProductResponseDto.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Ignore
    void 장비조회() {
        ProductResponseDto productResponseDto = webTestClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(PRODUCT_API)
                                .queryParam("productName", "애플 맥북 프로 15형 2019년형 MV912KH/A")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductResponseDto.class)
                .consumeWith(document("post",
                        requestParameters(
                                parameterWithName("productName").description("찾으려는 Product의 이름")
                        )))
                .returnResult()
                .getResponseBody();

        assertThat(productResponseDto).isEqualTo(ProductTestData.NAVER_PRODUCT_RESPONSE_DTO);
    }
}
