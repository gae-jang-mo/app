package com.gaejangmo.apiserver.model.userproduct.domain.converter;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.ProductType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ProductTypeAttributeConverter implements AttributeConverter<ProductType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final ProductType productType) {
        return productType.getCode();
    }

    @Override
    public ProductType convertToEntityAttribute(final Integer dbData) {
        return ProductType.ofCode(dbData);
    }
}
