package hry.activiti.process.upload;

import com.aliyun.oss.OSSClient;
import hry.util.upload.FileUploadStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 *  @author: liuchenghui
 *  @Date: 2020/3/27 17:07
 *  @Description: 阿里云上传文件策略
 */
@Component
public class OssFileUploadStrategy implements FileUploadStrategy {
    /**
     * @Description: Endpoint以杭州为例，其它Region请按实际情况填写。
     */
    @Value("${app.oss.endpoint}")
    private String endpoint;
    /**
     * @Description: 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
     */
    @Value("${app.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${app.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${app.oss.privateBucketName}")
    private String privateBucketName;
    @Value("${app.oss.publicBucketName}")
    private String publicBucketName;

    @Override
    public void uploadFile (InputStream inputStream, String fileName, boolean isPrivate) {
        OSSClient ossClient = null;
        try {
            String bucketName;
            if (isPrivate) {
                bucketName = privateBucketName;
            } else {
                bucketName = publicBucketName;
            }
            // 创建OSSClient实例。
            ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            ossClient.putObject(bucketName, fileName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                // 关闭OSSClient。
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getFileUrl (String objectName, boolean isPrivate) {
        String bucketName;
        if(isPrivate) {
            bucketName = privateBucketName;
        }else{
            bucketName = publicBucketName;
        }
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = null;
        if (checkFileIsExist(ossClient, bucketName, objectName)) {
            // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
            url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        return url == null ? "" : url.toString();
    }

    /**
     * 判断文件是否存在
     * @param ossClient oss客户端对象
     * @param bucketName 存储桶名称
     * @param objectName 文件名
     * @return
     */
    private boolean checkFileIsExist(OSSClient ossClient, String bucketName, String objectName) {
        try {
            return ossClient.doesObjectExist(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
