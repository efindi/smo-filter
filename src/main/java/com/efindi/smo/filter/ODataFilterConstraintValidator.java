package com.efindi.smo.filter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ODataFilterConstraintValidator implements ConstraintValidator<ODataFilter, String> {

    @Override
    public void initialize(ODataFilter constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return new ODataFilterValidatorImpl().isValid(value);
    }

}
