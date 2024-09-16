package com.example.livebetting.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PlaceBetRequestModelValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatePlaceBetRequestModel {
    String message() default "Cannot place more than 500 coupons at a time.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
