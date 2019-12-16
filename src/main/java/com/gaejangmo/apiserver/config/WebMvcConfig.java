package com.gaejangmo.apiserver.config;

import com.gaejangmo.apiserver.model.common.resolver.SecurityUserArgumentResolver;
import com.gaejangmo.apiserver.model.userproduct.controller.converter.ProductTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final SecurityUserArgumentResolver securityUserArgumentResolver;

    public WebMvcConfig(final SecurityUserArgumentResolver securityUserArgumentResolver) {
        this.securityUserArgumentResolver = securityUserArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(securityUserArgumentResolver);
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new ProductTypeConverter());
    }
}
