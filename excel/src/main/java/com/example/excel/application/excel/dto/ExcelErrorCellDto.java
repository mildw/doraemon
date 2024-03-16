package com.example.excel.application.excel.dto;

import com.example.excel.application.excel.enums.ExcelError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelErrorCellDto {

    private List<ExcelError> error;
    private String cellValue;
}
