package com.example.templatemethodpattern.application;

import org.springframework.stereotype.Service;

@Service
public class Case1Service extends TemplateAbstract {

    @Override
    CaseType getCase() {
        return CaseType.CASE_1;
    }

    @Override
    Object step1() {
        return "case 1 - step 1";
    }

    @Override
    Object step2() {
        return "case 1 - step 2";
    }

    @Override
    Object step3() {
        return "case 1 - step 3";
    }
}
