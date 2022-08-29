package utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 * Copyright:   互融云
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年10月14日 下午4:21:22
 */
public class DocumentHandler {
    private Configuration configuration = null;

    /**
     * 创建configuration
     *
     * @param ftlSrc 模版存放路径,ftl所在的文件夹路径
     */
    public DocumentHandler (String ftlSrc) {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        try {
            if (!"".equals(ftlSrc)) {
                configuration.setDirectoryForTemplateLoading(new File(ftlSrc));
            } else {
                configuration.setClassForTemplateLoading(this.getClass(), "/resources");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据ftl生成文件方法
     *
     * @param dataMap -- 数据集
     * @param fileSrc -- 生成后的文件地址
     * @param ftlName -- ftl模版名称
     */
    public void createOrderAndDown (Map<String, Object> dataMap, String fileSrc, String ftlName) {
        Template t = null;
        Writer out = null;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            // 输出文档路径及名称
            File outFile = new File(fileSrc);

            // 如果输出目标文件夹不存在，则创建
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }

            // 装载模板
            t = configuration.getTemplate(ftlName);

            fileOutputStream = new FileOutputStream(outFile);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            out = new BufferedWriter(outputStreamWriter);

            //生成
            t.process(dataMap, out);

            out.close();
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                //关闭流
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 要把模版生成合同
     *
     * @param dataMap 数据map集合
     * @param fileSrc 生成后的文件路径
     */
    public void createDoc (Map<String, Object> dataMap, String fileSrc) {
        //设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
        //这里我们的模板是放在/com/zhiwei/credit/util/xmlToWord/firstCreditor包下面
        Template t = null;
        try {
            //test.ftl为要装载的模板
            t = configuration.getTemplate("firstCreditor.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //输出文档路径及名称
        File outFile = new File(fileSrc);
        //如果输出目标文件夹不存在，则创建
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            //生成
            t.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                //关闭流
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
