package com.gaejangmo.apiserver.model.product.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {
    private List<String> validValues;

    @Override
    public void initialize(final EnumValue constraintAnnotation) {
        this.validValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return validValues.contains(value);
    }
}
