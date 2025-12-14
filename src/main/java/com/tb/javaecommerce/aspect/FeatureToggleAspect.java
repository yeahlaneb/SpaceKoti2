package com.tb.javaecommerce.aspect;

import com.tb.javaecommerce.annotations.FeatureToggle;
import com.tb.javaecommerce.exception.FeatureNotAvailableException;
import com.tb.javaecommerce.service.FeatureToggleService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Around("@annotation(featureToggle)")
    public Object around(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        String featureName = featureToggle.value();

        if (featureToggleService.isFeatureEnabled(featureName)) {
            return joinPoint.proceed();
        } else {
            throw new FeatureNotAvailableException("Feature '" + featureName + "' is disabled.");
        }
    }
}
