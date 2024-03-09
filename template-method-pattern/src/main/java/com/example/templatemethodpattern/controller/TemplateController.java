package com.example.templatemethodpattern.controller;

import com.example.templatemethodpattern.application.CaseType;
import com.example.templatemethodpattern.application.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @GetMapping("/template/step1")
    public Object step1(@RequestParam CaseType caseType) {
        return templateService.step1(caseType);
    }

    @GetMapping("/template/step2")
    public Object step2(@RequestParam CaseType caseType) {
        return templateService.step2(caseType);
    }

    @GetMapping("/template/step3")
    public Object step3(@RequestParam CaseType caseType) {
        return templateService.step3(caseType);
    }
}
