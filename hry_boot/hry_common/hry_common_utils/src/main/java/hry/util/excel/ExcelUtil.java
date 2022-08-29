package hry.util.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * Apache POI操作Excel对象
 * HSSF：操作Excel 2007之前版本(.xls)格式，生成的EXCEL不经过压缩直接导出
 * XSSF：操作Excel 2007及之后版本(.xlsx)格式，内存占用高于HSSF
 * SXSSF:从POI3.8 beta3开始支持,基于XSSF,低内存占用,专门处理大数据量(建议)。
 * 注意: 值得注意的是SXSSFWorkbook只能写(导出)不能读(导入)
 * </p>
 * 说明:
 * .xls格式的excel(最大行数65536行,最大列数256列)
 * .xlsx格式的excel(最大行数1048576行,最大列数16384列)
 *
 * @author:
 */
public class ExcelUtil {

    /**
     * @Description: 2003 版本的excel
     */
    private final static String Excel_2003 = ".xls";
    /**
     * @Description: 2007 版本的excel
     */
    private final static String Excel_2007 = ".xlsx";
    /**
     * @Description: 默认日期格式（类型为Date即可转换）
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * @Description: 默认列宽
     */
    public static final int DEFAULT_COLUMN_WIDTH = 17;

    /**
     * @param file 输入文件流
     * @author:
     * @Date: 2020/4/2 14:42
     * @Description: 导入Excel
     */
    public static List<List<Object>> importExcel (MultipartFile file)
            throws Exception {
        // 文件名
        String fileName = file.getOriginalFilename();
        // 后缀
        String xls = fileName.substring(fileName.lastIndexOf('.'));
        if (Excel_2003.equals(xls) || Excel_2007.equals(xls)) {
            // 导入操作
            return ExcelUtil.getImportExcel(file);
        } else {
            // 导入格式不正确
            System.out.println("导入格式不正确：导入失败！");
        }
        return null;
    }

    /**
     * 导出Excel
     *
     * @param titleList 标题及表格头信息集合
     * @param dataArray 数据数组
     * @param os        文件输出流
     */
    public static void exportExcel (List<LinkedHashMap> titleList, JSONArray dataArray, OutputStream os)
            throws Exception {
        ExcelUtil.getExportExcel(titleList, dataArray, os);
    }

    /**
     * 导入Excel
     *
     * @param file 导入文件流对象
     */
    private static List<List<Object>> getImportExcel (MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        // 将导入的Excel数据转换成list集合
        List<List<Object>> excelLists = ImportExcelUtil.getDataListByExcel(inputStream, fileName);
        return excelLists;
    }

    /**
     * 导出Excel
     *
     * @param titleList 表格头信息集合
     * @param dataArray 数据数组
     * @param os        文件输出流
     */
    private static void getExportExcel (List<LinkedHashMap> titleList, JSONArray dataArray, OutputStream os) {
        long startTime = System.currentTimeMillis();
        /**
         * 声明一个工作薄
         */
        // 大于1000行时会把之前的行(内存)写入硬盘
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
        workbook.setCompressTempFiles(true);
        try {
            // 表头标题样式
            CellStyle titleStyle = getTitleStyle(workbook);
            // 表头head样式
            CellStyle headerStyle = getHeaderStyle(workbook);
            // 单元格样式
            CellStyle cellStyle = getCellStyle(workbook);
            // 表标题
            String title = (String) titleList.get(0).get("title");
            // 获取表头显示字段
            LinkedHashMap<String, String> headMap = titleList.get(1);
            // 生成一个(带名称)表sheet
            SXSSFSheet sheet = workbook.createSheet(title);

            /**
             * 冻结表标题和表头
             * sheet.createFreezePane(a,b,c,d)
             *ａ表示要冻结的列数；
             *ｂ表示要冻结的行数；
             *ｃ表示右边区域[可见]的首列序号；
             *ｄ表示下边区域[可见]的首行序号；*/
            sheet.createFreezePane(0, 2, 0, 2);

            /**
             * 遍历数据集合，产生Excel行数据，
             * rowIndex 数据起始行为0，赋值为2（包括title + head，即第三行起）
             */
            excelDataHandler(dataArray, titleStyle, headerStyle, cellStyle, title, headMap, sheet);

            // 写入操作
            workbook.write(os);
            // 刷新此输出流并强制将所有缓冲的输出字节写出
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 释放workbook所占用的所有windows资源
            workbook.dispose();
            long endTime = System.currentTimeMillis();
            System.out.println("导出数据：" + dataArray.size() + "条，共耗时：" + (endTime - startTime) / 1000 + "秒。");
        }
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/4/2 15:49
     * @Description: 数据处理
     */
    private static void excelDataHandler (JSONArray dataArray, CellStyle titleStyle, CellStyle headerStyle, CellStyle cellStyle, String title, LinkedHashMap<String, String> headMap, SXSSFSheet sheet) {
        String datePattern = DEFAULT_DATE_PATTERN;
        int minBytes = DEFAULT_COLUMN_WIDTH;
        /**
         * 生成head相关信息 + 设置每列宽度
         */
        // 列宽数组
        int[] colWidthArr = new int[headMap.size()];
        // headKey数组
        String[] headKeyArr = new String[headMap.size()];
        // headVal数组
        String[] headValArr = new String[headMap.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : headMap.entrySet()) {
            headKeyArr[i] = entry.getKey();
            headValArr[i] = entry.getValue();

            int bytes = headKeyArr[i].getBytes().length;
            colWidthArr[i] = bytes < minBytes ? minBytes : bytes;
            // 设置列宽
            sheet.setColumnWidth(i, colWidthArr[i] * 256);
            i++;
        }

        int rowIndex = 0;
        for (Object obj : dataArray) {
            // 生成title+head信息
            if (rowIndex == 0) {
                // 设置title行内容及样式
                SXSSFRow titleRow = sheet.createRow(0);
                // 合并title行单元格
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));
                titleRow.createCell(0).setCellValue(title);
                titleRow.getCell(0).setCellStyle(titleStyle);

                // 设置head行内容及样式
                SXSSFRow headerRow = sheet.createRow(1);
                for (int j = 0; j < headValArr.length; j++) {
                    headerRow.createCell(j).setCellValue(headValArr[j]);
                    headerRow.getCell(j).setCellStyle(headerStyle);
                }
                rowIndex = 2;
            }
            // 转换数据格式
            JSONObject dataObj = (JSONObject) JSONObject.toJSON(obj);
            // 创建行，生成数据
            SXSSFRow dataRow = sheet.createRow(rowIndex);
            for (int k = 0; k < headKeyArr.length; k++) {
                // 创建单元格
                SXSSFCell cell = dataRow.createCell(k);
                // 根据头设置数据类型，并格式化
                Object o = dataObj.get(headKeyArr[k]);
                String cellValue = "";
                if (o == null) {
                    cellValue = "";
                } else if (o instanceof Date) {
                    cellValue = new SimpleDateFormat(datePattern).format(o);
                } else if (o instanceof Float || o instanceof Double) {
                    cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                } else {
                    cellValue = o.toString();
                }
                // 设置单元格及样式
                cell.setCellValue(cellValue);
                cell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/4/2 15:17
     * @Description: 获取单元格样式
     */
    private static CellStyle getCellStyle (SXSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        Font cellFont = workbook.createFont();
        // 是否加粗
        cellFont.setBold(false);
        // 字体大小
        cellFont.setFontHeight((short) 300);
        cellStyle.setFont(cellFont);
        return cellStyle;
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/4/2 15:17
     * @Description: 获取表头样式
     */
    private static CellStyle getHeaderStyle (SXSSFWorkbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 前景色纯色填充
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 边框
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        // 字体
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        // 是否加粗
        headerFont.setBold(true);
        // 字体大小
        headerFont.setFontHeight((short) 500);
        headerStyle.setFont(headerFont);
        return headerStyle;
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/4/2 15:16
     * @Description: 获取标题样式
     */
    private static CellStyle getTitleStyle (SXSSFWorkbook workbook) {
        CellStyle titleStyle = workbook.createCellStyle();
        // 水平居中
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置边框
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        // 字体
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        // 加粗
        titleFont.setBold(true);
        // 字体高度
        titleFont.setFontHeight((short) 700);
        titleStyle.setFont(titleFont);
        return titleStyle;
    }

}
