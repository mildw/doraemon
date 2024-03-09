package com.example.templatemethodpattern.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final List<TemplateAbstract> caseServices;
    private final EnumMap<CaseType, TemplateAbstract> caseMap = new EnumMap<>(CaseType.class);

    @PostConstruct
    public void init() {
        for (TemplateAbstract template : caseServices) {
            caseMap.put(template.getCase(), template);
        }
    }

    public Object step1(CaseType caseType) {

        TemplateAbstract caseService = caseMap.get(caseType);

        return caseService.step1();
    }

    public Object step2(CaseType caseType) {

        TemplateAbstract caseService = caseMap.get(caseType);

        return caseService.step2();
    }

    public Object step3(CaseType caseType) {

        TemplateAbstract caseService = caseMap.get(caseType);

        return caseService.step3();
    }

}
