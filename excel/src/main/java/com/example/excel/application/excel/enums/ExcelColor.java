package com.example.excel.application.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.xssf.usermodel.XSSFColor;

@Getter
@AllArgsConstructor
public enum ExcelColor {
  
    WHITE(new XSSFColor(new java.awt.Color(255, 255, 255), null)),  
    GREEN(new XSSFColor(new java.awt.Color(198, 224, 180), null)),  
    BLUE(new XSSFColor(new java.awt.Color(217, 225, 242), null)),  
    ORANGE(new XSSFColor(new java.awt.Color(255, 242, 204), null)),
    GRAY(new XSSFColor(new java.awt.Color(203, 203, 203), null)),  
    ERROR(new XSSFColor(new java.awt.Color(252, 228, 214), null)),  
    ;  
    public XSSFColor color;  
  
}