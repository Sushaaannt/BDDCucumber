package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.logging.Logger;


public class FileOperations {
    Workbook wb;
    Sheet sheet = null;
    Row row = null;
    Cell cell = null;
    FileInputStream fis = null;
    FileOutputStream fileOut = null;
    String XLSXFile;
    final static Logger logger = Logger.getLogger(String.valueOf(FileOperations.class));

    public FileOperations(String file) throws IOException {
        XLSXFile = file;
        File fileq = new File(file);
        if (!fileq.exists()) {
            createXLSXFile("sheet1");
            logger.info("created new file at location : " + file);
        }
        wb = new XSSFWorkbook(XLSXFile);
    }

    public void createXLSXFile(String sheetName) throws IOException {
        try {
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                XSSFSheet spreadsheet = workbook.createSheet(sheetName);
                XSSFRow rowhead = spreadsheet.createRow((short) 0);
                FileOutputStream fileOut = new FileOutputStream(XLSXFile);
                workbook.write(fileOut);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public boolean isFileExist() {
        boolean flag = false;
        File f = new File(XLSXFile);
        if (f.exists()) {
            logger.info("File Existed");
            flag = true;
        } else {
            logger.info("File Not Found!");
        }
        return flag;
    }

    public void removeFile() {
        File f = new File(XLSXFile);
        if (f.exists()) {
            f.delete();
            logger.info("File Deleted");
        } else {
            logger.info("File Not Found!");
        }
    }

    public boolean isXLSXSheetExist(String sheetName) {
        Sheet sh = wb.getSheet(sheetName);
        boolean flag;
        if (sh == null) {
            logger.info("sheet Not Present");
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    public boolean isXLSXColumnExist(String sheetName, String columnName) {
        Sheet sh = wb.getSheet(sheetName);
        boolean flag = false;
        if (sh == null) {
            logger.info("sheet Not Present");
        } else {
            for (int col = 0; col < getSheetColumnCount(sheetName); col++) {
                if (getXLSXCellData(sheetName, col, 1).equalsIgnoreCase(columnName)) {
                    flag = true;
                }
            }
            return flag;
        }
        return flag;
    }

    public int getSheetColumnCount(String sheetName) {
        int index = wb.getSheetIndex(sheetName);
        if (index == -1) {

        } else {
            sheet = wb.getSheetAt(index);
        }
        row = sheet.getRow(0);
        return row.getLastCellNum();
    }

    public String getXLSXCellData(String sheetName, int columnNumber, int rowNumber) {
        int index = wb.getSheetIndex(sheetName);
        String cellData;
        if (index == -1) {
            return "";
        } else {
            sheet = wb.getSheetAt(index);
            row = sheet.getRow(rowNumber - 1);
            if (row == null) {
                return "";
            } else {
                cell = row.getCell(columnNumber);
                if (cell == null)
                    return "";

                cellData = cell.getStringCellValue();
            }
        }
        return cellData;
    }
    public String getXLSXCellData(String sheetName, String columnName, int rowNumber) {
        int index = wb.getSheetIndex(sheetName);
        int columnIndex = 0;
        String cellData;
        int totalCell;
        int i;
        int rowNo;
        boolean flag =false;
        if (index == -1) {
            return "";
        } else {
            sheet = wb.getSheetAt(index);
            int rows = sheet.getLastRowNum();
            for (rowNo=0;rowNo<sheet.getLastRowNum();rowNo++){
                row=sheet.getRow(rowNo);
                totalCell= row.getLastCellNum();
                for (i=0; i< totalCell ; i++){
                    cellData=row.getCell(i).getStringCellValue();
                    if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(columnName.trim())){
                        columnIndex=i;
                        flag=true;
                        break;
                    }
                }
                if(flag){
                    break;
                }
            }
            row=sheet.getRow(rowNumber-1);
            if (row == null) {
                return "";
            } else {
                cell = row.getCell(columnIndex);
                if (cell == null)
                    return "";

                cellData = cell.getStringCellValue();
            }
        }
        return cellData;
    }
    public void cloneSheet(String sheetName) throws IOException {
        wb=new XSSFWorkbook();
        sheet=wb.cloneSheet(wb.getActiveSheetIndex());
        FileOutputStream fileOut = new FileOutputStream(XLSXFile);
        wb.write(fileOut);
        fileOut.close();
    }
    public String getSheetName(int sheetIndex){
        String sheetName=null;
        sheetName=wb.getSheetName(sheetIndex);
        return sheetName;
    }
    public void addColumn(String sheetName,String columnName) throws IOException {
        FileInputStream fileRead=new FileInputStream(XLSXFile);
        wb=new XSSFWorkbook(fileRead);
        int index = wb.getSheetIndex(sheetName);
        if(index==-1){
            
        }else {
            sheet=wb.getSheetAt(index);
        }
        row=sheet.getRow(0);
        if(row==null){
            row=sheet.createRow(0);
        }
        if(row.getLastCellNum()==-1){
            cell=row.createCell(row.getLastCellNum());
        }
        cell.setCellValue(columnName);
        FileOutputStream fileOut = new FileOutputStream(XLSXFile);
        wb.write(fileOut);
        fileOut.close();
    }
    public int getCellRowInDescendingOrder(String sheetName,String colName,String cellValue){
        for(int i= getRowCount(sheetName);i>=2;i--){
            if(getXLSXCellData(sheetName,colName,i).equalsIgnoreCase(cellValue)){
                return i;
            }
        }
        return -1;
    }
    public int getRowCount(String sheetName){
        int index =wb.getSheetIndex(sheetName);
        if (index == -1) {
            return 0;
        }else {
            sheet=wb.getSheetAt(index);
            return sheet.getLastRowNum()+1;
        }
    }
    public int getcellRowNum(String sheetName,String colName,String cellValue){
        for(int i= getRowCount(sheetName);i>=2;i--){
            if(getXLSXCellData(sheetName,colName,i).equalsIgnoreCase(cellValue)){
                return i;
            }
        }
        return -1;
    }
    public boolean setXLSXCellData(String sheetname,String columnName,int rowNumber,String data){
        try {
            fis=new FileInputStream(XLSXFile);
            wb=new XSSFWorkbook(fis);
            if(rowNumber<=0)
                return false;
            int index =wb.getSheetIndex(sheetname);
            int colNum=-1;
            if(index==-1)
                return false;
            
            sheet=wb.getSheetAt(index);
           row=sheet.getRow(0);
           
           for (int i =0 ; i<row.getLastCellNum();i++){
               try{
                   if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(columnName)){
                       colNum=i;
                   }
               }catch (Exception e){
                   logger.info(e.getMessage());
               }
           }
           if (colNum==-1)
               return false;
           sheet.autoSizeColumn(colNum);
           row=sheet.getRow(rowNumber-1);
           if(row==null)
               row=sheet.createRow(rowNumber-1);
           
           cell=row.getCell(colNum);
           if (cell==null)
               cell=row.createCell(colNum);
           
           cell.setCellValue(data);
           fileOut=new FileOutputStream(XLSXFile);
           wb.write(fileOut);
           fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public String SetXLSXCellColorYellow(String sheetName,String columnName,int rowNumber) throws IOException {
        fis=new FileInputStream(XLSXFile);
        wb=new XSSFWorkbook(fis);
        int index =wb.getSheetIndex(sheetName);
        int colIndex=0;
        String cellData=null;
        if(index==-1)
            return "";
        else {
            sheet=wb.getSheetAt(index);
            row=sheet.getRow(0);

            for (int i =0 ; i<row.getLastCellNum();i++){
                
                    if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(columnName)){
                        colIndex=i;
                        break;
                    }
            }
            row=sheet.getRow(rowNumber-1);
            if(row==null)
                return "";
            else {
                cell=row.getCell(colIndex);
                CellStyle style=wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                //style.setFillForegroundColor(IndexedColors.RED.getIndex());
                //style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
                style.setFillForegroundColor(FillPatternType.SOLID_FOREGROUND.getCode());
                cell.setCellStyle(style);
                fileOut=new FileOutputStream(XLSXFile);
                wb.write(fileOut);
                fileOut.close();
            }
            return cellData;
        }
    }
}
