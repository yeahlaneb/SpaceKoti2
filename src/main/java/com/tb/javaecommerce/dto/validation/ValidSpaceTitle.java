package com.tb.javaecommerce.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpaceTitleValidator.class)
@Documented
public @interface ValidSpaceTitle {
    String MESSAGE = "Invalid Space Title: Space Title must contain: star | galaxy | comet";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
