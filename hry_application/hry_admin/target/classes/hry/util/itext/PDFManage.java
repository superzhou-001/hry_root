package hry.util.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import hry.business.ct.model.CtContractTemplateElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yaozh
 * @Description:
 * @Date:
 */
@Slf4j
public class PDFManage {
    /**
     * @param imageUrllist       图片路径集合
     * @param mOutputPdfFileName 输出的pdf文件名
     * @return
     */
    public static byte[] Pdf(ArrayList<String> imageUrllist, String mOutputPdfFileName) {
        Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(mOutputPdfFileName));
            doc.open();
            for (int i = 0; i < imageUrllist.size(); i++) {
                doc.newPage();
                Image png1 = Image.getInstance(imageUrllist.get(i));
                float heigth = png1.getHeight();
                float width = png1.getWidth();
                int percent = getPercent2(heigth, width);
                png1.setAlignment(Image.MIDDLE);
                png1.scalePercent(percent + 3); //表示是原来图像的比例;
                doc.add(png1);
            }
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        File mOutputPdfFile = new File(mOutputPdfFileName);
        if (!mOutputPdfFile.exists()) {
            mOutputPdfFile.deleteOnExit();
            return null;
        }
        return addText(mOutputPdfFile);
    }

    public static byte[] addText(File mOutputPdfFile) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        PdfContentByte pdfContentByte = null;
        try {
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            InputStream inputStream = new FileInputStream(mOutputPdfFile); // 读取默认pdf文件
            pdfReader = new PdfReader(inputStream); // 加载文件到pdf引擎
            byteArrayOutputStream = new ByteArrayOutputStream();
            pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream); // 加载模板

            for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                PdfContentByte over = pdfStamper.getOverContent(i);
                ColumnText columnText = new ColumnText(over);
                // llx 和 urx  最小的值决定离左边的距离. lly 和 ury 最大的值决定离下边的距离
                columnText.setSimpleColumn(0, 500, 10000, 0);
                Paragraph elements = new Paragraph(0, new Chunk("我是大神qeqweqeqe"));
                // 设置字体，如果不设置添加的中文将无法显示
                Font font = new Font(baseFont);
                elements.setFont(font);
                columnText.addElement(elements);
                columnText.go();
            }
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.error("addText(String, int, int) - Exception e=" + e); //$NON-NLS-1$
            }
        } catch (DocumentException e) {
            e.printStackTrace();
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

    public static byte[] addText(File mOutputPdfFile, List<CtContractTemplateElement> elementList) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        PdfContentByte pdfContentByte = null;
        try {
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            InputStream inputStream = new FileInputStream(mOutputPdfFile); // 读取默认pdf文件
            pdfReader = new PdfReader(inputStream); // 加载文件到pdf引擎
            byteArrayOutputStream = new ByteArrayOutputStream();
            pdfStamper = new PdfStamper(pdfReader, byteArrayOutputStream); // 加载模板
            Rectangle pageSize = pdfReader.getPageSize(1);
            float height = pageSize.getHeight();//pdf的高
            float width = pageSize.getWidth();//pdf的宽
            for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                for (CtContractTemplateElement ctContractTemplateElement : elementList) {
                    if (ctContractTemplateElement.getPageNum() == i) {
                        PdfContentByte over = pdfStamper.getOverContent(i);
                        ColumnText columnText = new ColumnText(over);
                        // llx 和 urx  最小的值决定离左边的距离. lly 和 ury 最大的值决定离下边的距离
                        columnText.setSimpleColumn(Float.valueOf(ctContractTemplateElement.getWidth()), Float.valueOf(ctContractTemplateElement.getHeight()), 10000, 0);
                        Paragraph elements = new Paragraph(0, new Chunk(ctContractTemplateElement.getElementName()));
                        // 设置字体，如果不设置添加的中文将无法显示
                        Font font = new Font(baseFont);
                        font.setSize(ctContractTemplateElement.getTextSize());
                        elements.setFont(font);
                        columnText.addElement(elements);
                        columnText.go();
                    }
                }

            }
        } catch (IOException e) {
            if (log.isDebugEnabled()) {
                log.error("addText(String, int, int) - Exception e=" + e); //$NON-NLS-1$
            }
            return null;
        } catch (DocumentException e) {
            e.printStackTrace();
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

    /**
     * 第一种解决方案在不改变图片形状的同时，判断，如果h>w，则按h压缩，否则在w>h或w=h的情况下，按宽度压缩
     *
     * @param h
     * @param w
     * @return
     */

    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 297 / h * 100;
        } else {
            p2 = 210 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    /**
     * 第二种解决方案，统一按照宽度压缩这样来的效果是，所有图片的宽度是相等的，自我认为给客户的效果是最好的
     */
    public static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }

    /**
     * 根据文件地址返回 File
     * @param url 地址
     * @return
     */
    public static File doGetDownload(String url) {
        String separator=File.separator;
        String dir="ExistingEvidence/"+separator+"downLoad/";
        String fileName=System.currentTimeMillis()+".pdf";
        String path = dir+fileName;
        HttpClient httpClient = null;
        HttpGet httpPost = null;
        try {
            httpClient = createSSLClientDefault();
            httpPost = new HttpGet(url);
            //ProxyHttpClient.getProxyHttpClient(httpClient);
            HttpResponse response = httpClient.execute(httpPost);
            if (response == null) {
                throw new RuntimeException("HttpResponse is null.");
            }
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (null == entity) {
                    throw new RuntimeException("HttpEntity is null.");
                }
                // 下载成功返回文件流
                if (!"application/zip".equals(response.getEntity().getContentType().getValue()) && !"application/pdf".equals(response.getEntity().getContentType().getValue())) {
                    // 下载失败返回json格式报文
                    return null;
                }
                byte[] result = EntityUtils.toByteArray(response.getEntity());
                BufferedOutputStream bw = null;
                try {
                    File f = new File(path); // 创建文件对象
                    if (f.exists()) { // 重复时候替换掉
                        f.delete();
                    }
                    if (!f.getParentFile().exists()) { // 创建文件路径
                        f.getParentFile().mkdirs();
                    }
                    bw = new BufferedOutputStream(new FileOutputStream(path));// 写入文件
                    bw.write(result);
                    return f;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                throw new RuntimeException("connect fail. http_status:" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            try {
                httpClient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 用于进行Https请求的HttpClient
     *
     * @author joey
     *
     */
    public static CloseableHttpClient createSSLClientDefault(){
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return  HttpClients.createDefault();
    }

    /*public static void main(String[] args) throws IOException, DocumentException {
        ArrayList<String> imageUrllist = new ArrayList<String>();
        imageUrllist.add("C:\\Users\\lenovo\\Desktop\\11.png");
        imageUrllist.add("C:\\Users\\lenovo\\Desktop\\22.jpg");
        String pdfUrl = "C:\\Users\\lenovo\\Desktop\\\\33.pdf";
        File file = PDFManage.Pdf(imageUrllist, pdfUrl);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



}
