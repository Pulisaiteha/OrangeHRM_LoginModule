package ORANGEHRM.OrangeHRM;

import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {

    public static Object[][] getTestData(String filePath, String sheetName) {
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            int totalRows = sheet.getPhysicalNumberOfRows();
            int totalCols = 2; // username and password

            Object[][] data = new Object[totalRows][totalCols];

            for (int i = 0; i < totalRows; i++) { // start from row 0 now
                XSSFRow row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < totalCols; j++) {
                        XSSFCell cell = row.getCell(j);
                        data[i][j] = (cell != null && !cell.toString().isBlank()) ? cell.toString() : "";
                    }
                } else {
                    data[i][0] = "";
                    data[i][1] = "";
                }
            }

            workbook.close();
            file.close();
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
