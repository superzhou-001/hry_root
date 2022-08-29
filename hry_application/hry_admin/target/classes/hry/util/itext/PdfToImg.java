package hry.util.itext;

import hry.util.UUIDUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yaozh
 * @Description:
 * @Date:
 */
public class PdfToImg {
    public static final float dpi = 100;
    /**
     * <p>Description:pdf文件转img文件 </p>
     */
    public static void pdfToImg(MultipartFile multipartFile, HttpServletResponse response) throws Exception {
        File file = multipartFileToFile(multipartFile);
        PDDocument pdDocument = null;
        try {
            pdDocument = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            /* 第二位参数越大转换后越清晰，相对转换速度越慢 */
            BufferedImage image = renderer.renderImageWithDPI(0, dpi);
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            pdDocument.close();
        }
    }


    /**
     * 	 将多页pdf转化为多张图片
     * @return 转化后的图片的路径集合
     * @throws IOException
     */
    public static List<ByteArrayOutputStream> pdfPathToImagePaths(MultipartFile multipartFile,String fileUrl, HttpServletResponse response) throws Exception {
        File pdfFile = null;
        if(multipartFile!=null){
            pdfFile = multipartFileToFile(multipartFile);
        }else{
            pdfFile = PDFManage.doGetDownload(fileUrl);
        }


        PDDocument pdDocument = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        List<ByteArrayOutputStream> list = new ArrayList<>();
        try {
            pdDocument = PDDocument.load(pdfFile);
            int pageCount = pdDocument.getNumberOfPages();
            PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
            for (int pageIndex=0; pageIndex<pageCount; pageIndex++) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, dpi, ImageType.RGB);
                ImageIO.write(image, "png", byteArrayOutputStream);
                list.add(byteArrayOutputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //PdfToImg.delteTempFile(pdfFile);
            pdDocument.close();
            byteArrayOutputStream.close();
            if (pdfFile != null) {
                pdfFile.delete();
            }
        }
        return list;
    }



    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            boolean b = del.delete();
            System.out.println(b);
        }
    }
}
