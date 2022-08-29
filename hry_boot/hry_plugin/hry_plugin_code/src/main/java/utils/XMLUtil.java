/**
 * Copyright:   互融云
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年10月14日 下午4:21:22
 */
package utils;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.List;

/**
 * <p> TODO</p>		dom4J 操作XML
 * @author:         Liu Shilei
 * @Date :          2015年10月14日 下午4:21:22
 */
public class XMLUtil {


	public static void main(String[] args) {
		 Document doc = XMLUtil.readXML("E:\\javaCodeMyEclipse15\\20150915\\web\\hurong_base\\src\\main\\resources\\backup\\menu\\menu.xml");
		 Element rootElement = doc.getRootElement();
		 List<Element> list1 = rootElement.elements("menu");
		 if(list1!=null){
			 for(Element element1 : list1){
				 System.out.println(element1.attributeValue("name"));
				 System.out.println(element1.attributeValue("id"));

				 List<Element> list2 = element1.elements("menu");
				 if(list2!=null){
					 for(Element element2 :list2 ){
						 System.out.println(element2.attributeValue("name"));
						 System.out.println(element2.attributeValue("id"));
					 }
				 }


			 }
		 }


	}

	/**
	 * 创建document
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @return
	 * @return: Document
	 * @Date :          2015年10月14日 下午4:52:49
	 * @throws:
	 */
	public static Document createDocument(){
		 // 1.document构建器
        Document doc = DocumentHelper.createDocument();
        return doc;
	}


	/**
	 * 生成createXML
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @param rootElement  要节点
	 * @param:    @param fileSrc   文件保存路径
	 * @return: void
	 * @Date :          2015年10月14日 下午4:27:45
	 * @throws:
	 */
	public static boolean createXML(Document doc,String fileSrc){

	    File file =new File(fileSrc);
        if(!file.exists()){
           FileUtil.createFile(fileSrc);
        }

        boolean flag = true;
        XMLWriter writer = null;
        try {
			//设置XML文档输出格式
        	OutputFormat format = OutputFormat.createPrettyPrint();
        	format.setEncoding("UTF-8");
             // 设置换行
        	format.setNewlines(true);
             // 生成缩进
        	format.setIndent(true);
             // 使用4个空格进行缩进, 可以兼容文本编辑器
        	format.setIndent("    ");
            writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(fileSrc), "UTF-8"),format);
            writer.write(doc);
            writer.close();
        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }  finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;

	}



	/**
	 * 读取一个XML
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @param fileSrc XML文件路径
	 * @param:    @return  document
	 * @return: Document
	 * @Date :          2015年10月14日 下午5:38:51
	 * @throws:
	 */
	public static Document readXML(String fileSrc){
		SAXReader reader = new SAXReader();
		Document doc = null;
	    try {
	    	doc = reader.read(new File(fileSrc));
		} catch (DocumentException e) {
			System.out.println("no find "+ fileSrc);
		}
	    return doc;
	}

	/**
	 * 读取一个流
	 * @param is
	 * @return
	 */
	public static Document readIS(InputStream is){
		SAXReader reader = new SAXReader();
		Document doc = null;
	    try {
	    	doc = reader.read(is);
	    	if(is!=null){
	    		is.close();
	    	}
		} catch (Exception e) {
			System.out.println("no find ");
		} finally {
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	    return doc;
	}






}
