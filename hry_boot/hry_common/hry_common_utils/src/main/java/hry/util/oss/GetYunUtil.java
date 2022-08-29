package hry.util.oss;


import hry.util.properties.PropertiesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static hry.util.oss.AWSUtil.getUrlFromS3;


public class GetYunUtil {
    public static void upload(InputStream inputStream, String objectName,boolean isPrivate)  {
       String type = PropertiesUtils.APP.getProperty("app.oss.type");
       switch (type){
           case "oss":OssUtil.upload(inputStream,objectName,isPrivate);break;
           case "azure":AzureUtil.upload(inputStream,objectName);break;
           case "aws":AWSUtil.uploadToS3(inputStream,objectName,isPrivate);break;
           default:break;
       }
    }

    public static String getUrl(String objectName,boolean isPrivate) {
        String type = PropertiesUtils.APP.getProperty("app.oss.type");
        switch (type){
            case "oss": return OssUtil.getUrl(objectName,isPrivate);
            case "azure": return PropertiesUtils.APP.getProperty("app.azure.azureUrl")+objectName;
            case "aws": return AWSUtil.getUrlFromS3(objectName,isPrivate);
            default: return null;
        }
    }

    public static void main (String[] args) throws Exception {
        String key = "hryfile/11112/111/1111.jpg";
        File tempFile = new File("E:\\ideaWorkspace\\proj\\schcg\\hurong_schcg_front\\src\\main\\webapp\\static\\src\\img\\erweima.jpg");
        GetYunUtil.upload( new FileInputStream(tempFile), key,false);//上传文件
        String url = getUrlFromS3(key);//获取文件的url
        System.out.println(url);
    }
}
