package hry.util.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yaozh
 * @Description:
 * @Date:
 */
@Slf4j
public class PdfUtils {

    static String               PDF    = "C:\\Users\\lenovo\\Desktop\\123.pdf";
    public static final String  NAME   = "STSong-Light";
    public static final String  ENCODE = "UniGB-UCS2-H";
    public static final int     SIZE   = 12;

    /**
     * 生成添加文字数据流
     *
     * @param text
     *            输入信息
     * @param width
     *            宽度
     * @param height
     *            高度
     * @return 数据流
     */
    public static byte[] addText(String text, int width, int height) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        PdfContentByte pdfContentByte = null;
        try {
            BaseFont baseFont = BaseFont.createFont(NAME, ENCODE, BaseFont.NOT_EMBEDDED);
            InputStream inputStream = new FileInputStream(new File(PDF)); // 读取默认pdf文件
            pdfReader = new PdfReader(inputStream); // 加载文件到pdf引擎
            byteArrayOutputStream = new ByteArrayOutputStream();
            pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream); // 加载模板
            pdfContentByte = pdfStamper.getOverContent(1); // 获取顶部

            for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                PdfContentByte over = pdfStamper.getOverContent(i);
                ColumnText columnText = new ColumnText(over);
                // llx 和 urx  最小的值决定离左边的距离. lly 和 ury 最大的值决定离下边的距离
                columnText.setSimpleColumn(0, 500, 10000, 0);
                Paragraph elements = new Paragraph(0, new Chunk("我是甲方"));
                // 设置字体，如果不设置添加的中文将无法显示
                Font font = new Font(baseFont);
                elements.setFont(font);
                columnText.addElement(elements);
                columnText.go();
            }


            /*pdfContentByte.beginText(); // 插入文字信息
            pdfContentByte.setFontAndSize(baseFont, SIZE);
            BaseColor baseColor = new BaseColor(0, 0, 0);
            pdfContentByte.setColorFill(baseColor);
            pdfContentByte.setTextMatrix(100, 100); // 设置文字在页面中的坐标
            text = "123113131313131313131==============";
            pdfContentByte.showText(text);
            pdfContentByte.endText();*/


        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("addText(String, int, int) - Exception e=" + e); //$NON-NLS-1$
            }
        } finally {
            try {
                pdfStamper.close();
                pdfReader.close();
                byteArrayOutputStream.close();
            } catch (Exception _e) {
                if (log.isDebugEnabled()) {
                    log.error("addText(String, int, int) - Exception e=" + _e); //$NON-NLS-1$
                }
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void main(String[] args) throws Exception{
        Map<String, Object> data = new HashMap<String, Object>();//要插入的数据
        data.put("productInfo_organProdFullName", "测试");
        //初始化itext
        //设置编码
        BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
        PdfReader pdfReader=new PdfReader(PDF);
        PdfStamper pdfStamper=new PdfStamper(pdfReader, new FileOutputStream(PDF));
        AcroFields form = pdfStamper.getAcroFields();
        form.addSubstitutionFont(baseFont);

        //写入数据
        for(String key : data.keySet()){
            String value=data.get(key).toString();
            //key对应模板数据域的名称
            form.setField(key,value, true);
        }

//        //添加图片
//        int pageNo = form.getFieldPositions("img").get(0).page;
//        Rectangle signRect = form.getFieldPositions("img").get(0).position;
//        float x = signRect.getLeft();
//        float y = signRect.getBottom();
//        Image image = Image.getInstance("图片路径");
//        PdfContentByte under = pdfStamper.getOverContent(pageNo);
//        //设置图片大小
//        image.scaleAbsolute(signRect.getWidth(), signRect.getHeight());
//        //设置图片位置
//        image.setAbsolutePosition(x, y);
//        under.addImage(image);

        //设置不可编辑
        pdfStamper.setFormFlattening(true);
        pdfStamper.close();
    }

}
