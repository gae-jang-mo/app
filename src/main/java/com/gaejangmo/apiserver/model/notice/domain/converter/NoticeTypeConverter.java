package com.gaejangmo.apiserver.model.notice.domain.converter;

import com.gaejangmo.apiserver.model.notice.domain.NoticeType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class NoticeTypeConverter implements AttributeConverter<NoticeType, String> {
    @Override
    public String convertToDatabaseColumn(final NoticeType attribute) {
        return attribute.getName();
    }

    @Override
    public NoticeType convertToEntityAttribute(final String dbData) {
        return NoticeType.ofName(dbData);
    }
}
