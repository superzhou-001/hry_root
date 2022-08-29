package hry.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 转换类
 * <p>
 * 说明:
 * .xls格式的excel(最大行数65536行,最大列数256列)
 * .xlsx格式的excel(最大行数1048576行,最大列数16384列)
 */
public class ImportExcelUtil {

    //2003 版本的excel
    private final static String Excel_2003 = ".xls";
    //2007 版本的excel
    private final static String Excel_2007 = ".xlsx";

    /**
     * @param in 输入流
     * @param fileName 文件名
     * @return
     */
    public static List<List<Object>> getDataListByExcel (InputStream in, String fileName) throws Exception {
        List<List<Object>> list = null;
        //创建Excel工作簿
        Workbook work = getWorkbook(in, fileName);
        if (work == null) {
            throw new Exception("创建Excel工作簿为空！");
        }
        // 初始化 工作簿、行、列、数据对象
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        list = new ArrayList<>();

        //遍历Excel中的所有sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            //获取当前sheet中的所有行，如果excel有格式，这种方式取值不准确
            int totalRow = sheet.getPhysicalNumberOfRows();
            for (int j = sheet.getFirstRowNum(); j < totalRow; j++) {
                row = sheet.getRow(j);
                // 判断行是否为空
                if (!isRowEmpty(row)) {
                    //获取第一个单元格的数据是否存在
                    Cell fristCell = row.getCell(0);
                    if (fristCell != null) {
                        //遍历所有的列
                        List<Object> li = new ArrayList<Object>();
                        for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                            cell = row.getCell(y);
                            // 获取单元格中的值
                            String callCal = getCellValue(cell) + "";
                            li.add(callCal);
                        }
                        list.add(li);
                    }
                } else {
                    continue;
                }
            }
        }
        // 关闭流
        in.close();
        return list;
    }

    /**
     * 判断行是否为空
     *
     * @param row
     * @return false：否，true：是
     */
    private static boolean isRowEmpty (Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }
        return true;
    }

    /**
     * 描述：根据文件后缀，自动适应上传文件的版本
     *
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    private static Workbook getWorkbook (InputStream inStr, String fileName) throws Exception {
        Workbook work = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (Excel_2003.equals(fileType)) {
            //2003 版本的excel
            work = new HSSFWorkbook(inStr);
        } else if (Excel_2007.equals(fileType)) {
            //2007 版本的excel
            work = new XSSFWorkbook(inStr);
        } else {
            throw new Exception("解析文件格式有误！");
        }
        return work;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    private static Object getCellValue (Cell cell) {
        String result = new String();
        switch (cell.getCellType()) {
            //Excel公式
            case HSSFCell.CELL_TYPE_FORMULA:
                try {
                    result = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    result = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            // 数字类型
            case HSSFCell.CELL_TYPE_NUMERIC:
                // 处理日期格式、时间格式
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                            .getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {
                        // 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
                    double value = cell.getNumericCellValue();
                    Date date = DateUtil.getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#.##");
                    }
                    result = format.format(value);
                }
                break;
            // String类型
            case HSSFCell.CELL_TYPE_STRING:
                result = cell.getRichStringCellValue().toString();
                break;
            // 空处理
            case HSSFCell.CELL_TYPE_BLANK:
                result = "";
            default:
                result = "";
                break;
        }
        return result;
    }

    /**
     * 获取字符串中的数字订单号、数字金额等，
     * 如从"USD 374.69"中获取到374.69、从“交易单号：123456789”获取到123456789
     *
     * @return
     */
    private static String getFormatNumber (String str) {
        str = str.trim();
        Pattern p = Pattern.compile("[0-9]");
        int indexNum = 0;
        int lenght = str.length();
        String num = "";
        for (int i = 0; i < lenght; i++) {
            num += str.charAt(i);
            Matcher m = p.matcher(num);
            if (m.find()) {
                indexNum = i;
                break;
            }
        }
        String formatNumber = str.substring(indexNum, lenght);
        return formatNumber;
    }
}
