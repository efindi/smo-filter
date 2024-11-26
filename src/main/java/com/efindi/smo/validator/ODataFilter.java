package com.efindi.smo.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {ODataFilterConstraintValidator.class})
public @interface ODataFilter {

  String message() default "Invalid/Unsupported OData $filter format";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}