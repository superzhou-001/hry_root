package hry.util.oss;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import hry.util.properties.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * 亚马逊AWS S3工具类
 */
public class AWSUtil {

    public static void uploadToS3(InputStream inputStream, String objectName) {
        uploadToS3(inputStream,objectName,false);
    }

    private static String getBucketName(boolean isPrivate) {
        if(isPrivate && StringUtils.isNotEmpty(PropertiesUtils.APP.getProperty("app.aws.hasPrivate"))) {
            //私有库
            return PropertiesUtils.APP.getProperty("app.aws.bucketNamePrivate");
        }else {
            //公有库
            return PropertiesUtils.APP.getProperty("app.aws.bucketName");
        }
    }

    public static S3ObjectInputStream getContentFromS3 (String remoteFileName) throws IOException {
        return getContentFromS3(remoteFileName,false);
    }

    public static String getUrlFromS3 (String remoteFileName) throws IOException {
        return getUrlFromS3(remoteFileName,false);
    }

    /**
     * 将文件上传至S3上
     * @param inputStream 上传文件流
     * @param objectName 文件名称
     */
    public static void uploadToS3 (InputStream inputStream, String objectName,Boolean isPrivate) {
        try {
            /**
             * 为了提高 AWS 账户的安全性，我们建议您使用 IAM 用户 (而不使用根账户凭证) 来提供访问凭证,
             * 请登录 https://docs.aws.amazon.com/zh_cn/sdk-for-java/v1/developer-guide/signup-create-iam-user.html进行注册
             */

            // IAM 用户的访问密钥 ID
            String AWS_ACCESS_KEY = PropertiesUtils.APP.getProperty("app.aws.accessKeyId");
            // 秘密访问密钥
            String AWS_SECRET_KEY = PropertiesUtils.APP.getProperty("app.aws.accessKeySecret");
            // 设置 AWS 区域
            String AP_NORTHEAST_2 = PropertiesUtils.APP.getProperty("app.aws.region");
            // 设置存储桶名称
            String bucketName = getBucketName(isPrivate);
            System.out.println("初始化开始-----------------");
            // 初始化AWS S3客户端配置
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)))
                    .withRegion(AP_NORTHEAST_2)
                    .build();
            System.out.println("初始化完毕-----------------");
            // 查看是否存在buckName
            if (!checkBucketExists(s3, bucketName)) {
                s3.createBucket(bucketName);
            }
            // 上传文件
            ObjectMetadata metadata = new ObjectMetadata();
            s3.putObject(new PutObjectRequest(bucketName, objectName, inputStream, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            //获取一个request
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
                    bucketName, objectName);
            //生成公用的url
            URL url = s3.generatePresignedUrl(urlRequest);
            System.out.println("---aws url --- = " + url);
        } catch (Exception ase) {
            ase.printStackTrace();
        }
    }

    /**
     * 验证s3上是否存在名称为bucketName的Bucket
     *
     * @param s3
     * @param bucketName
     * @return
     */
    public static boolean checkBucketExists (AmazonS3 s3, String bucketName) {
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket bucket : buckets) {
            if (Objects.equals(bucket.getName(), bucketName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param @param  remoteFileName
     * @param @throws IOException    设定文件
     * @return S3ObjectInputStream    返回类型  数据流
     * @throws
     * @Title: getContentFromS3
     * @Description: 获取文件2进制流
     */
    public static S3ObjectInputStream getContentFromS3 (String remoteFileName,Boolean isPrivate) throws IOException {
        try {
            /**
             * 为了提高 AWS 账户的安全性，我们建议您使用 IAM 用户 (而不使用根账户凭证) 来提供访问凭证,
             * 请登录 https://docs.aws.amazon.com/zh_cn/sdk-for-java/v1/developer-guide/signup-create-iam-user.html进行注册
             */

            // IAM 用户的访问密钥 ID
            String AWS_ACCESS_KEY = PropertiesUtils.APP.getProperty("app.aws.accessKeyId");
            // 秘密访问密钥
            String AWS_SECRET_KEY = PropertiesUtils.APP.getProperty("app.aws.accessKeySecret");
            // 设置 AWS 区域
            String AP_NORTHEAST_2 = PropertiesUtils.APP.getProperty("app.aws.region");
            // 设置存储桶名称
            String bucketName = getBucketName(isPrivate);
            // 初始化AWS S3客户端配置
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)))
                    .withRegion(AP_NORTHEAST_2)
                    .build();
            GetObjectRequest request = new GetObjectRequest(bucketName, remoteFileName);
            S3Object object = s3.getObject(request);
            S3ObjectInputStream inputStream = object.getObjectContent();
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param @param  remoteFileName 文件名
     * @param @return
     * @param @throws IOException    设定文件
     * @return String    返回类型
     * @throws
     * @Title: getUrlFromS3
     * @Description: 获取文件的url
     */
    public static String getUrlFromS3 (String remoteFileName,Boolean isPrivate){
        try {
            /**
             * 为了提高 AWS 账户的安全性，我们建议您使用 IAM 用户 (而不使用根账户凭证) 来提供访问凭证,
             * 请登录 https://docs.aws.amazon.com/zh_cn/sdk-for-java/v1/developer-guide/signup-create-iam-user.html进行注册
             */

            // IAM 用户的访问密钥 ID
            String AWS_ACCESS_KEY = PropertiesUtils.APP.getProperty("app.aws.accessKeyId");
            // 秘密访问密钥
            String AWS_SECRET_KEY = PropertiesUtils.APP.getProperty("app.aws.accessKeySecret");
            // 设置 AWS 区域
            String AP_NORTHEAST_2 = PropertiesUtils.APP.getProperty("app.aws.region");
            // 设置存储桶名称
            String bucketName = getBucketName(isPrivate);
            // 初始化AWS S3客户端配置
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)))
                    .withRegion(AP_NORTHEAST_2)
                    .build();
            GeneratePresignedUrlRequest httpRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName);
            String url = s3.generatePresignedUrl(httpRequest).toString();//临时链接
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main (String[] args) throws Exception {
        String key = "hryfile/1/2/tempImg.jpg";
        File tempFile = new File("E:\\ideaWorkspace\\proj\\schcg\\hurong_schcg_front\\src\\main\\webapp\\static\\src\\img\\erweima.jpg");
        uploadToS3(new FileInputStream(tempFile), key);//上传文件
        String url = getUrlFromS3(key);//获取文件的url
        System.out.println(url);
    }
}
