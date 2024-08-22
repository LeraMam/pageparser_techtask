package org.techtask.parse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.techtask.data.Item;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.List;

public class ExelParser {
    public static void parseExelSheet(Workbook workbook, String sheetName, List<Item> items) {
        Sheet sheet = workbook.createSheet(sheetName);
        items.sort(Comparator.comparingDouble(Item::getPrice));
        createSheet(sheet, items);
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            sheet.autoSizeColumn(i);
        }
        createExelFile(workbook);
    }

    public static void createSheet(Sheet sheet, List<Item> items) {
        Row mainRow = sheet.createRow(0);
        mainRow.createCell(0).setCellValue("Name");
        mainRow.createCell(1).setCellValue("Price");
        mainRow.createCell(2).setCellValue("Href");
        for (int i = 0; i < items.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(items.get(i).getName());
            row.createCell(1).setCellValue(items.get(i).getPrice());
            row.createCell(2).setCellValue(items.get(i).getHref());
        }
    }

    public static void createExelFile(Workbook workbook) {
        try {
            FileOutputStream out = new FileOutputStream(
                    new File("excel.xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
