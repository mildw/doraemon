package com.example.excel.application.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ExcelErrorRowDto {
    private ExcelErrorCellDto organization;
    private ExcelErrorCellDto position;
    private ExcelErrorCellDto employeeId;
    private ExcelErrorCellDto name;
    private ExcelErrorCellDto fruit;
}
