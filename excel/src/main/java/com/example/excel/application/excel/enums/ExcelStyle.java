package com.example.excel.application.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

@Getter
@AllArgsConstructor
public enum ExcelStyle {

    WHITE_LEFT_BOLD_BLACK(
            ExcelColor.WHITE.getColor(),
            HorizontalAlignment.LEFT,
            BorderStyle.NONE,
            true, IndexedColors.BLACK
    ),
    GREEN_BORDER_BOLD_BLACK(
            ExcelColor.GREEN.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            true, IndexedColors.BLACK
    ),

    GREEN_BORDER_BLACK(
            ExcelColor.GREEN.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            false, IndexedColors.BLACK
    ),

    GREEN_BORDER_BOLD_RED(
            ExcelColor.GREEN.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            true, IndexedColors.RED
    ),
    BLUE_BORDER_BOLD_BLACK(
            ExcelColor.BLUE.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            true, IndexedColors.BLACK
    ),
    BLUE_BORDER_BLACK(
            ExcelColor.BLUE.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            false, IndexedColors.BLACK
    ),
    BLUE_BORDER_BOLD_RED(
            ExcelColor.BLUE.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            true, IndexedColors.RED
    ),
    GRAY_BORDER_BLACK(
            ExcelColor.GRAY.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            false, IndexedColors.BLACK
    ),
    ORANGE_BORDER_BOLD_BLACK(
            ExcelColor.ORANGE.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            true, IndexedColors.BLACK
    ),
    ORANGE_BORDER_BOLD_RED(
            ExcelColor.ORANGE.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            true, IndexedColors.RED
    ),
    WHITE_BLACK(
            ExcelColor.WHITE.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.NONE,
            false, IndexedColors.BLACK
    ),
    WHITE_BOLDER_BLACK(
            ExcelColor.WHITE.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            false, IndexedColors.BLACK
    ),
    ERROR_RED(
            ExcelColor.ERROR.getColor(),
            HorizontalAlignment.CENTER,
            BorderStyle.THIN,
            false, IndexedColors.RED
    ),
    ;
    private XSSFColor color;
    private HorizontalAlignment alignment;
    private BorderStyle borderStyle;
    private Boolean fontBold;
    private IndexedColors fontColor;

    public XSSFCellStyle getCellStyle(Workbook workbook) {
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();

        cellStyle.setAlignment(alignment);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        if (color.equals(ExcelColor.WHITE.getColor())) {
            cellStyle.setFillPattern(FillPatternType.NO_FILL);
        }
        cellStyle.setBorderTop(borderStyle);
        cellStyle.setBorderBottom(borderStyle);
        cellStyle.setBorderLeft(borderStyle);
        cellStyle.setBorderRight(borderStyle);

        Font font = workbook.createFont();
        font.setBold(fontBold);
        font.setColor(fontColor.getIndex());
        cellStyle.setFont(font);
        cellStyle.setFont(font);

        return cellStyle;
    }

}