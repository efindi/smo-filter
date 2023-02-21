package com.efindi.smo.filter;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { ODataFilterConstraintValidator.class })
public @interface ODataFilter {

    String message() default "Invalid/Unsupported OData $filter format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}