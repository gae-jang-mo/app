package com.gaejangmo.apiserver.model.userproduct.controller.converter;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import org.springframework.core.convert.converter.Converter;


public class ProductTypeConverter implements Converter<String, ProductType> {

    @Override
    public ProductType convert(final String name) {
        return ProductType.ofName(name);
    }
}
