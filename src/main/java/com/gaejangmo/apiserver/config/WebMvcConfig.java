package com.gaejangmo.apiserver.config;

import com.gaejangmo.apiserver.model.userproduct.controller.converter.ProductTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        registry.addConverter(new ProductTypeConverter());
    }
}
