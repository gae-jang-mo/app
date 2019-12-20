package com.gaejangmo.apiserver.model.notice.domain.converter;

import com.gaejangmo.apiserver.model.notice.domain.NoticeType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NoticeTypeConverter implements AttributeConverter<NoticeType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(final NoticeType attribute) {
        return attribute.getCode();
    }

    @Override
    public NoticeType convertToEntityAttribute(final Integer dbData) {
        return NoticeType.ofCode(dbData);
    }
}
