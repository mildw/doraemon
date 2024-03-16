package com.example.excel.application.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExcelError {

    not_supported_FRUIT("지원 하지 않는 과일 입니다."),
    ;

    private String errorText;
}
