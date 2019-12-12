package com.gaejangmo.apiserver.model.user.domain.converter;

import com.gaejangmo.apiserver.model.user.domain.vo.Grade;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GradeAttributeConverter implements AttributeConverter<Grade, Integer> {
    @Override
    public Integer convertToDatabaseColumn(final Grade attribute) {
        return attribute.getCode();
    }

    @Override
    public Grade convertToEntityAttribute(final Integer dbData) {
        return Grade.ofCode(dbData);
    }
}
