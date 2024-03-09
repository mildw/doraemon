package com.example.multitenancy.controller;

import com.example.multitenancy.application.MultitenancyService;
import com.example.multitenancy.controller.rqrs.MultitenancyRs;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MultiTenancyController {

    private final MultitenancyService multitenancyService;

    @GetMapping("/members/name")
    public String getMemberNames() {
        return multitenancyService.getMemberNames();
    }

    @GetMapping("/multi-tenancy")
    public MultitenancyRs test() {
        return multitenancyService.test();
    }
}
