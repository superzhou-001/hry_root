package hry.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: yaozh
 * @Description:
 * @Date:
 */
public class ExcelUtil {
    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";

    /**
     * 导出excel
     * @param sheetName
     * @param title
     * @param values
     * @param wb
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        //style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        // 声明列对象
        HSSFCell cell = null;

        // 创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        // 创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                // 将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }

    /**
     * @Title: analyExcel
     * @Description: 解析Excel
     * @param file
     */
    @SuppressWarnings("deprecation")
    public static List<List<String>> analyExcel(File file) throws FileNotFoundException, IOException {
        Workbook wb = getWorkbook(file);
        Sheet sheet = wb.getSheetAt(0);
        List<List<String>> list = new ArrayList<List<String>>();
        for(int i=0;i<=sheet.getLastRowNum();i++){
            Row row = sheet.getRow(i);
            List<String> list0 = new ArrayList<String>();
            //添加判断第一个单元格是否为空，为空这行不读
            int firstCellNum = row.getFirstCellNum();
            row.getCell(firstCellNum).setCellType(Cell.CELL_TYPE_STRING);
            if(null == row.getCell(firstCellNum) || "".equals(row.getCell(firstCellNum).getStringCellValue().trim())) continue;
            for(int j=0;j<row.getLastCellNum();j++){

                //null表示单元格未使用过
                if (row.getCell(j) != null) {
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    if(!"".equals(row.getCell(j).getStringCellValue().trim())){
                        list0.add(row.getCell(j).getStringCellValue());
                    }else{
                        list0.add("");
                    }
                } else {
                    list0.add("");
                }
            }
            list.add(list0);
        }
        return list;

    }
    /**
     * @Title: getWorkbook
     * @Description: 根据文件后缀获取不同的工作簿
     * @param file
     * @throws
     */
    public static Workbook getWorkbook(File file) throws IOException {
        Workbook workbook = null;
        String filePath = file.getName();
        InputStream is = new FileInputStream(file);
        if (filePath.endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (filePath.endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(is);
        }
        return workbook;
    }
    /**
     * @Title: getWorkbook
     * @Description: 根据文件后缀获取不同的工作簿
     * @param inputStream
     * @param extension  文件
     * @return
     * @throws IOException     后缀
     * @throws
     */
    public static Workbook getWorkbook(InputStream inputStream,String extension) throws IOException {
        Workbook workbook = null;
        if (extension.endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (extension.endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        if(inputStream != null ){
            inputStream.close();
        }
        return workbook;
    }
    /**
     * 文件检查
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    public static void preReadCheck(File file) throws FileNotFoundException, FileFormatException {
        // 常规检查
        //File file = new File(filePath);
        String filePath = file.getName();
        if (!file.exists()) {
            throw new FileNotFoundException("传入的文件不存在：" + filePath);
        }
        if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
            throw new FileFormatException("传入的文件不是excel");
        }
    }
    /**
     * 取单元格的值
     * @param cell 单元格对象
     * @param treatAsStr 为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Object getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }
        if (treatAsStr) {
            // 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
            // 加上下面这句，临时把它当做文本来读取
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if(DateUtil.isCellDateFormatted(cell)){
                Date theDate = cell.getDateCellValue();
                return hry.util.date.DateUtil.dateToString(theDate,"yyyy-MM-dd");
            }else{
                return cell.getNumericCellValue();
            }
        } else {
            return cell.getStringCellValue();
        }
    }
}
