package com.tb.javaecommerce.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class SpaceTitleValidator implements ConstraintValidator<ValidSpaceTitle, String> {

    private static final String TITLE_KEYWORD_PATTERN = "(?i)\\b(star|galaxy|comet)\\b";

    private static final Pattern pattern = Pattern.compile(TITLE_KEYWORD_PATTERN);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && pattern.matcher(value).find();
    }
}
