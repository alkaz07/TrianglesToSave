package com.example.observablelists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelSaver {

    public static void save(List<ObservableTriangles> list, String fname)
    {
        Workbook wb = new HSSFWorkbook();
      //  Workbook wb = new XSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet = wb.createSheet("list of triangles");

        int i=0;
        for (ObservableTriangles tr: list  ) {
            // Create a row and put some cells in it. Rows are 0 based.
            Row row = sheet.createRow((short)i);
// Create a cell and put a value in it.
            Cell cell = row.createCell(0);
            cell.setCellValue(tr.sideA.get());

            cell = row.createCell(1);
            cell.setCellValue(tr.sideB.get());

            cell = row.createCell(2);
            cell.setCellValue(tr.sideC.get());

            cell = row.createCell(3);
            cell.setCellValue(tr.area());
            i++;
        }


        try {
// Write the output to a file
            FileOutputStream fileOut = new FileOutputStream(fname);
            wb.write(fileOut);
            fileOut.close();
        }
        catch(IOException e)
        {
            System.out.println("не удалось сохранить");
        }
    }
}
