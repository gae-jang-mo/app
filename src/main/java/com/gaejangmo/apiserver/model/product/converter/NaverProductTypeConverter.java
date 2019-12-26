package com.gaejangmo.apiserver.model.product.converter;

import com.gaejangmo.apiserver.model.product.domain.vo.NaverProductType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NaverProductTypeConverter implements AttributeConverter<NaverProductType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final NaverProductType naverProductType) {
        return naverProductType.getValue();
    }

    @Override
    public NaverProductType convertToEntityAttribute(final Integer dbData) {
        return NaverProductType.find(dbData);
    }
}
