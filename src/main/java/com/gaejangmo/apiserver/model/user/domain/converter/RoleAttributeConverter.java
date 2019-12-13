package com.gaejangmo.apiserver.model.user.domain.converter;

import com.gaejangmo.apiserver.model.user.domain.vo.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleAttributeConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final Role role) {
        return role.getCode();
    }

    @Override
    public Role convertToEntityAttribute(final Integer dbData) {
        return Role.ofCode(dbData);
    }
}
