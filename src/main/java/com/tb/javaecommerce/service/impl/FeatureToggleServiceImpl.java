package com.tb.javaecommerce.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class FeatureToggleServiceImpl implements FeatureToggleService {

    private final Environment environment;

    public FeatureToggleServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public boolean isFeatureEnabled(String featureName) {
        return Boolean.parseBoolean(environment.getProperty(featureName, "false"));
    }
}
