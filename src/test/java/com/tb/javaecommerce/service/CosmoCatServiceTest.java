package com.tb.javaecommerce.service;

import com.tb.javaecommerce.exception.FeatureNotAvailableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "feature.cosmoCats.enabled=true"
})
class CosmoCatServiceTestEnabled {
    @Autowired
    private CosmoCatService cosmoCatService;

    @Test
    void testFeatureEnabled() {
        assertFalse(cosmoCatService.getCosmoCats().isEmpty());
    }
}

@SpringBootTest
@TestPropertySource(properties = {
    "feature.cosmoCats.enabled=false"
})
class CosmoCatServiceTestDisabled {
    @Autowired
    private CosmoCatService cosmoCatService;

    @Test
    void testFeatureDisabled() {
        assertThrows(FeatureNotAvailableException.class,
            () -> cosmoCatService.getCosmoCats());
    }
}

