package com.tb.javaecommerce.web;

import com.tb.javaecommerce.service.CosmoCatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;

    @GetMapping("/cosmo-cats")
    public String getCosmoCats() {
        return cosmoCatService.getCosmoCats();
    }
}
