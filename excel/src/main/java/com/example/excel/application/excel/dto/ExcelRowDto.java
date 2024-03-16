package com.example.excel.application.excel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelRowDto {
    private String organization;
    private String position;
    private String employeeId;
    private String name;
    private String fruit;
}
