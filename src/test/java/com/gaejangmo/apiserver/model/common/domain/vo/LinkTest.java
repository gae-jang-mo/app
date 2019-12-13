package com.gaejangmo.apiserver.model.common.domain.vo;

import com.gaejangmo.apiserver.model.product.exception.UrlFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LinkTest {

    @Test
    void 유효한_링크() {
        assertThatCode(() ->
                Link.of("https://search.shopping.naver.com/detail/detail.nhn?cat_id=50000151&nv_mid=20571500240"))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http://",
            "http://.",
            "http://..",
            "http://../",
            "http://?",
            "http://??",
            "http://??/",
            "http://#",
            "http://##",
            "http://##/",
            "http://foo.bar?q=Spaces should be encoded",
            "//",
            "//a",
            "///a",
            "///",
            "http:///a",
            "foo.com",
            "rdar://1234",
            "h://test",
            "http:// shouldfail.com",
            ":// should fail",
            "http://foo.bar/foo(bar)baz quux",
            "ftps://foo.bar/",
            "http://-error-.invalid/",
            "http://-a.b.co",
            "http://a.b-.co",
            "http://0.0.0.0",
            "http://3628126748",
            "http://.www.foo.bar/",
            "http://www.foo.bar./",
            "http://.www.foo.bar./"
    })
    void 유효하지_않은_링크(String url) {
        assertThrows(UrlFormatException.class, () -> Link.of(url));
    }
}