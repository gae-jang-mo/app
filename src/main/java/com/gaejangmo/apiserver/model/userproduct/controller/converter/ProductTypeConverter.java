package com.gaejangmo.apiserver.model.userproduct.controller.converter;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;
import org.springframework.core.convert.converter.Converter;


// TODO: 2019/12/10 위치 여기하는게 맞을까?
public class ProductTypeConverter implements Converter<String, ProductType> {

    @Override
    public ProductType convert(final String name) {
        return ProductType.ofName(name);
    }
}
