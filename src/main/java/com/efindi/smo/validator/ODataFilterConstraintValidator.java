package com.efindi.smo.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ODataFilterConstraintValidator implements ConstraintValidator<ODataFilter, String> {

  @Override
  public void initialize(ODataFilter constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return new ODataFilterValidatorImpl().isValid(value);
  }

}
