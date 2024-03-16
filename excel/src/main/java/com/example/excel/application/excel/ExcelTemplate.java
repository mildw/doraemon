package com.example.excel.application.excel;

import com.example.excel.application.excel.dto.ExcelErrorCellDto;
import com.example.excel.application.excel.dto.ExcelErrorRowDto;
import com.example.excel.application.excel.dto.ExcelRowDto;
import com.example.excel.application.excel.enums.ExcelError;
import com.example.excel.application.excel.enums.ExcelHeader;
import com.example.excel.application.excel.enums.ExcelIndex;
import com.example.excel.application.excel.enums.ExcelStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;  
import org.apache.poi.ss.util.CellRangeAddressList;  
import org.apache.poi.xssf.streaming.SXSSFWorkbook;  
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.web.multipart.MultipartFile;
  
import java.io.InputStream;  
import java.util.ArrayList;  
import java.util.List;

import static com.example.excel.application.excel.enums.ExcelHeader.*;
import static com.example.excel.application.excel.enums.ExcelIndex.*;

public class ExcelTemplate {  
  
    private Workbook workbook;  
    private Sheet sheet;  
  
    public ExcelTemplate() {
        workbook = new SXSSFWorkbook();  
        sheet = workbook.createSheet("엑셀 양식");  
    }

    public static List<ExcelRowDto> of(MultipartFile excel) {
        List<ExcelRowDto> excelEvaluations = new ArrayList<>();
  
        try {  
            InputStream inputStream = excel.getInputStream();  
            Workbook workbook = WorkbookFactory.create(inputStream);  
            Sheet sheet = workbook.getSheetAt(0);  
  
            for (int i = ExcelIndex.getFirstRow(); i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);  
                if (row == null) {  
                    continue;  
                }  
  
                String organization = getValue(row, ORGANIZATION_INDEX.getCellNum());
                String position = getValue(row, POSITION_INDEX.getCellNum());
                String employeeId = getValue(row, EMPLOYEE_ID_INDEX.getCellNum());
                String name = getValue(row, NAME_INDEX.getCellNum());
                String fruit = getValue(row, FRUIT_INDEX.getCellNum());
  
                if (organization.isBlank() && position.isBlank() && employeeId.isBlank()
                        && name.isBlank() && fruit.isBlank()) {
                    continue;  
                }  
  
                ExcelRowDto excelEvaluation = new ExcelRowDto(organization, position, employeeId, name, fruit);
  
                excelEvaluations.add(excelEvaluation);  
            }  
  
        } catch (Exception e) {  
            throw new RuntimeException();
        }  
  
        return excelEvaluations;  
    }  
  
    public Workbook createExcelTemplate() {  
  
        for (ExcelHeader header : ExcelHeader.values()) {
            setHeader(sheet, header);  
        }  
  
        setColumWidth();  
        setIndexDefaultStyle(100);  
  
        return workbook;  
  
    }  
  
    public Workbook errorResultExcel(List<ExcelErrorRowDto> errorRow) {
        createExcelTemplate();  
  
        for (int i = 0; i < errorRow.size(); i++) {  
            int rowNum = ExcelIndex.getFirstRow() + i;
            Row row = getRow(sheet, rowNum);  
            ExcelErrorRowDto error = errorRow.get(i);
  
            setErrorComment(row, ORGANIZATION_INDEX, error.getOrganization());
            setErrorComment(row, POSITION_INDEX, error.getPosition());
            setErrorComment(row, EMPLOYEE_ID_INDEX, error.getEmployeeId());
            setErrorComment(row, NAME_INDEX, error.getName());
            setErrorComment(row, FRUIT_INDEX, error.getFruit());
        }  
  
        return workbook;  
    }  
  
    private void setErrorComment(Row row, ExcelIndex targetEmployeeId, ExcelErrorCellDto error) {
        Cell targetEmployeeIdCell = getCell(row, targetEmployeeId.getCellNum());  
        targetEmployeeIdCell.setCellValue(error.getCellValue());
        targetEmployeeIdCell.setCellComment(getComment(row.getRowNum(), targetEmployeeId.getCellNum(), error));  
        if (targetEmployeeIdCell.getCellComment() != null) {  
            targetEmployeeIdCell.setCellStyle(ExcelStyle.ERROR_RED.getCellStyle(workbook));  
        }  
    }  
  
    private Comment getComment(int i, int j, ExcelErrorCellDto error) {  
        if(error.getError().isEmpty()) {  
            return null;  
        }  
        Drawing<?> drawing = sheet.createDrawingPatriarch();  
        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0,  
                j, i, j + 3, i + error.getError().size());  
        Comment comment = drawing.createCellComment(anchor);  
        comment.setString(new XSSFRichTextString(errorToString(error.getError())));  
        return comment;  
    }  
  
    private String errorToString(List<ExcelError> errors) {  
        StringBuilder sb = new StringBuilder();  
        for (ExcelError error : errors) {
            sb.append(error.getErrorText()).append("\n");  
        }  
  
        return sb.toString();  
    }  
  
    private void setColumWidth() {  
  
        sheet.setColumnWidth(조직.getFirstCol(), 15 * 256);
        sheet.setColumnWidth(직책.getFirstCol(), 15 * 256);
        sheet.setColumnWidth(사번.getFirstCol(), 15 * 256);
        sheet.setColumnWidth(이름.getFirstCol(), 15 * 256);
        sheet.setColumnWidth(과일.getFirstCol(), 15 * 256);
    }  
  
    private void setIndexDefaultStyle(Integer indexNum) {  
  
        Integer firstRow = ExcelIndex.getFirstRow();
        Integer lastRow = ExcelIndex.getFirstRow() + indexNum;
        CellStyle indexCellStyle = ExcelStyle.WHITE_BLACK.getCellStyle(workbook);
  
        for (int i = firstRow; i < lastRow; i++) {  
            Row row = getRow(sheet, i);  
            getCell(row, ORGANIZATION_INDEX.getCellNum()).setCellStyle(indexCellStyle);
            getCell(row, POSITION_INDEX.getCellNum()).setCellStyle(indexCellStyle);
            getCell(row, EMPLOYEE_ID_INDEX.getCellNum()).setCellStyle(indexCellStyle);
            getCell(row, NAME_INDEX.getCellNum()).setCellStyle(indexCellStyle);
            getCell(row, FRUIT_INDEX.getCellNum()).setCellStyle(indexCellStyle);
        }  
  
        CellRangeAddressList addressList = new CellRangeAddressList(
                firstRow, lastRow, FRUIT_INDEX.getCellNum(), FRUIT_INDEX.getCellNum());

        String[] selectBox = {"사과", "포도", "오렌지"};
        DataValidationHelper validationHelper = sheet.getDataValidationHelper();  
        DataValidationConstraint constraint = validationHelper.createExplicitListConstraint(selectBox);
        DataValidation dataValidation = validationHelper.createValidation(constraint, addressList);  
        sheet.addValidationData(dataValidation);  
  
    }  
  
    private void setHeader(Sheet sheet, ExcelHeader header) {
        CellStyle cellStyle = header.getHeaderStyle().getCellStyle(workbook);  
        CellRangeAddress cellRangeAddress = header.mergedRegion();  
  
        for (int i = header.getFirstCol(); i <= header.getLastCol(); i++) {  
            Row row = getRow(sheet, header.getFirstRow());  
            Cell cell = getCell(row, i);  
  
            cell.setCellStyle(cellStyle);  
            cell.setCellValue(header.getExcelText());  
        }  
  
        if (cellRangeAddress.getNumberOfCells() > 1) {  
            sheet.addMergedRegion(cellRangeAddress);  
        }  
  
    }  
  
    private static String getValue(Row row, int i) {  
        Cell cell = getCell(row, i);  
        if (cell == null) {  
            return null;  
        }  
        return getCellValueAsString(cell).trim();  
    }  
  
    private static String getCellValueAsString(Cell cell) {  
        switch (cell.getCellType()) {  
            case STRING:  
                return cell.getStringCellValue();  
            case NUMERIC:  
                return String.valueOf((long) cell.getNumericCellValue());  
            case BOOLEAN:  
                return String.valueOf(cell.getBooleanCellValue());  
            case FORMULA:  
                return cell.getCellFormula();  
            case BLANK:  
                return "";  
            case ERROR:  
                return String.valueOf(cell.getErrorCellValue());  
            default:  
                return "";  
        }  
    }  
  
    private static Cell getCell(Row row, int i) {  
        Cell cell = row.getCell(i);  
        if (cell == null) {  
            cell = row.createCell(i);  
        }  
        return cell;  
    }  
  
    private Row getRow(Sheet sheet, int i) {  
        Row row = sheet.getRow(i);  
        if (row == null) {  
            row = sheet.createRow(i);  
        }  
        return row;  
    }  
  
}