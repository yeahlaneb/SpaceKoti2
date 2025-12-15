package com.tb.javaecommerce.service;

import com.tb.javaecommerce.annotations.FeatureToggle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosmoCatService {

    @FeatureToggle("feature.cosmoCats.enabled")
    public String getCosmoCats() {
        return String.join(", ", List.of("Moon", "Solar", "Galaxy"));
    }
}
