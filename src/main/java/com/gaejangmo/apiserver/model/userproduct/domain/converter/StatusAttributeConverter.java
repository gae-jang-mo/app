package com.gaejangmo.apiserver.model.userproduct.domain.converter;

import com.gaejangmo.apiserver.model.userproduct.domain.vo.Status;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StatusAttributeConverter implements AttributeConverter<Status, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Status attribute) {
        return attribute.getCode();
    }

    @Override
    public Status convertToEntityAttribute(final Integer dbData) {
        return Status.ofCode(dbData);
    }
}
