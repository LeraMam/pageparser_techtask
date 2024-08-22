package org.techtask;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Element;
import org.techtask.parse.PageParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        String constUrlPart="https://www.electromarket.by/mouse";

        PageParser pageParser = new PageParser(constUrlPart);
        List<Element> itemNames = pageParser.getItemNameList();
        List<Element> itemPrices = pageParser.getItemPriceList();
        List<String> itemHrefs = pageParser.getItemHrefList();
        System.out.println(itemNames.size() + "\n");
        for(int i = 0; i < itemNames.size(); i++){
            System.out.println(itemNames.get(i).text() + "\t" + itemPrices.get(i).text() + "\t" + itemHrefs.get(i));
        }


        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("excel-sheet");

        Row mainRow = sheet.createRow(0);
        mainRow.createCell(0).setCellValue("Name");
        mainRow.createCell(1).setCellValue("Price");
        mainRow.createCell(2).setCellValue("Href");

        for (int i = 0; i < itemNames.size(); i++) {
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(itemNames.get(i).text());
            row.createCell(1).setCellValue(itemPrices.get(i).text());
            row.createCell(2).setCellValue(itemHrefs.get(i));
        }

        /*Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue(1);
        row1.createCell(1).setCellValue("John William");
        row1.createCell(2).setCellValue(9999999);
        row1.createCell(3).setCellValue("william.john@gmail.com");
        row1.createCell(4).setCellValue("700000.00");*/


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