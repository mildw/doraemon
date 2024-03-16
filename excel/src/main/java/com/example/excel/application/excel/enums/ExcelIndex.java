package com.example.excel.application.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExcelIndex {
  
    ORGANIZATION_INDEX(0, ExcelStyle.GRAY_BORDER_BLACK),  
    POSITION_INDEX(1, ExcelStyle.GRAY_BORDER_BLACK),  
    EMPLOYEE_ID_INDEX(2, ExcelStyle.GRAY_BORDER_BLACK),  
    NAME_INDEX(3, ExcelStyle.GRAY_BORDER_BLACK),
    FRUIT_INDEX(4, ExcelStyle.GRAY_BORDER_BLACK),

    ;  
    private final Integer cellNum;  
    private final ExcelStyle style;  
  
    public static Integer getFirstRow() {  
        return 2;
    }  
}