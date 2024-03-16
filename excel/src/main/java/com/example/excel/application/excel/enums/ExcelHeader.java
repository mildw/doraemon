package com.example.excel.application.excel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.util.CellRangeAddress;

import static com.example.excel.application.excel.enums.ExcelIndex.*;

@Getter
@AllArgsConstructor
public enum ExcelHeader {

    TITLE(0,
            0,
            0,
            4,
            "엑셀 템플릿",
            ExcelStyle.WHITE_LEFT_BOLD_BLACK
    ),

    조직(
            1,
            1,
            ORGANIZATION_INDEX.getCellNum(),
            ORGANIZATION_INDEX.getCellNum(),
            "조직",
            ExcelStyle.GREEN_BORDER_BOLD_BLACK
    ),

    직책(1,
            1,
            POSITION_INDEX.getCellNum(),
            POSITION_INDEX.getCellNum(),
            "직책",
            ExcelStyle.GREEN_BORDER_BOLD_BLACK),
    사번(1,
            1,
            EMPLOYEE_ID_INDEX.getCellNum(),
            EMPLOYEE_ID_INDEX.getCellNum(),
            "사원 번호",
            ExcelStyle.GREEN_BORDER_BOLD_BLACK),
    이름(1,
            1,
            NAME_INDEX.getCellNum(),
            NAME_INDEX.getCellNum(),
            "이름",
            ExcelStyle.GREEN_BORDER_BOLD_BLACK),
    과일(1,
            1,
            FRUIT_INDEX.getCellNum(),
            FRUIT_INDEX.getCellNum(),
            "과일",
            ExcelStyle.GREEN_BORDER_BOLD_BLACK),
    ;

    private final Integer firstRow;
    private final Integer lastRow;
    private final Integer firstCol;
    private final Integer lastCol;
    private final String excelText;
    private final ExcelStyle headerStyle;

    public CellRangeAddress mergedRegion() {
        return new CellRangeAddress(this.firstRow, this.lastRow, this.firstCol, this.lastCol);
    }

}