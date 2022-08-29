package hry.util.itext;

/**
 * @Author: yaozh
 * @Description:
 * @Date:
 */
import java.io.File;
import java.io.IOException;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
/**
 * itextpdf中绝对位置添加文本，也可以进行多页设置
 * @author Administrator
 *
 */
public class ITextPdf {
    public static boolean generateContract() {
        try (PdfReader reader = new PdfReader("C:\\Users\\lenovo\\Desktop\\33.pdf");
             PdfWriter writer = new PdfWriter("C:\\Users\\lenovo\\Desktop\\qq.pdf");
             PdfDocument pdfDoc = new PdfDocument(reader, writer)) {
            Document document = new Document(pdfDoc);

            //PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

            //页数从1开始
            int pageNumber = 1;
            //单位应该是px
            float left = 0;
            float bottom = 0;
            float width = 200;
            document.add(new Paragraph("123").setFixedPosition(pageNumber, left, bottom, width));
            document.add(new Paragraph("456").setFixedPosition(1, 200, bottom, width).setHeight(500));
            System.out.println("————————————————————————————————————我在这里");
            document.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static void main(String[] args) {
        ITextPdf.generateContract();
    }

}