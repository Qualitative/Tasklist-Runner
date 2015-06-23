package com.ns.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;

import com.ns.model.Task;

public class ExcelConverter {

    private File chartTemplate;

    public ExcelConverter(Resource chartTemplate) throws IOException {
        this.chartTemplate = chartTemplate.getFile();
    }

    public void generateExcelChart(List<Task> tasks, File file) throws IOException, InvalidFormatException {

        int columnTitleRow = 1;
        int rowNum = tasks.size();

        Workbook workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream(chartTemplate)));

        Sheet sheet = workbook.getSheetAt(0);
        String sheetName = sheet.getSheetName();

        sheet.createRow(0).createCell(0).setCellValue("Name");
        sheet.getRow(0).createCell(1).setCellValue("MemoryUsage");

        Cell taskNameCell = null;
        Cell memoryUsageCell = null;

        int rowNumber = 0;
        for (Task task : tasks) {
            rowNumber++;
            Row row = sheet.getRow(rowNumber);
            if (row == null) {
                row = sheet.createRow(rowNumber);
            }

            taskNameCell = row.getCell(0);
            memoryUsageCell = row.getCell(1);

            if (taskNameCell == null) {
                String taskName = task.getName();
                int pid = task.getPid();
                if (pid == Task.DEFAULT) {
                    row.createCell(0).setCellValue(taskName);
                } else {
                    row.createCell(0).setCellValue(taskName + "[" + pid + "]");
                }
            } else {
                taskNameCell.setCellValue(task.getName());
            }

            if (memoryUsageCell == null) {
                row.createCell(1).setCellValue(task.getMemoryUsage());
            } else {
                memoryUsageCell.setCellValue(task.getMemoryUsage());
            }
        }

        Name rangeCell = workbook.getName("TaskName");
        String reference = sheetName + "!$A$" + (columnTitleRow + 1) + ":$A$" + (rowNum + columnTitleRow);
        rangeCell.setRefersToFormula(reference);
        rangeCell = workbook.getName("MemoryUsage");
        reference = sheetName + "!$B$" + (columnTitleRow + 1) + ":$B$" + (rowNum + columnTitleRow);
        rangeCell.setRefersToFormula(reference);

        FileOutputStream f = new FileOutputStream(file);
        workbook.write(f);
        f.close();
        workbook.close();
    }
}
