package org.techtask;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.techtask.data.Item;
import org.techtask.parse.ExelParser;
import org.techtask.parse.PageParser;

import java.io.IOException;
import java.util.List;


public class HtmlDataParseApplication {

    public static void main(String[] args) throws IOException {
        String constUrlPartMouse = "https://www.electromarket.by/mouse";
        String constUrlPartToaster = "https://www.electromarket.by/toaster";
        String constUrlPartElectricGrill = "https://www.electromarket.by/electricgrill";

        Workbook workbook = new XSSFWorkbook();

        PageParser mouseParser = new PageParser(constUrlPartMouse);
        List<Item> mouseList = mouseParser.getItemList();
        ExelParser.parseExelSheet(workbook, "mice", mouseList);

        PageParser toasterParser = new PageParser(constUrlPartToaster);
        List<Item> toasterList = toasterParser.getItemList();
        ExelParser.parseExelSheet(workbook, "toasters", toasterList);

        PageParser electricGrillParser = new PageParser(constUrlPartElectricGrill);
        List<Item> electricGrillList = electricGrillParser.getItemList();
        ExelParser.parseExelSheet(workbook, "electric grills", electricGrillList);

    }
}