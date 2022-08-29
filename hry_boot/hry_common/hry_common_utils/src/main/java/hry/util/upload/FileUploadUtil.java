package hry.util.upload;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author: liuchenghui
 * @Date: 2020/3/29 13:24
 * @Description: 文件上传工具类，可多文件可单文件
 * 返回上传文件在云服务器的相对路径
 */
public class FileUploadUtil {

    /**
     * @author: liuchenghui
     * @Date: 2020/3/29 13:25
     * @Description: 文件上传方法
     */
    public static List<String> uploadFile(FileUploadContext fileUploadContext, MultipartFile[] files, String implClassName, boolean isPrivate) {
        List<String> pathImg = new ArrayList<>(5);
        try {
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    if (file != null) {
                        // 获取源文件名
                        String filename = file.getOriginalFilename();
                        // 判断文件是否上传
                        if (filename != null && filename.length() > 0) {
                            // 新文件名称
                            String newFileName = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
                            newFileName = new StringBuilder("hryfile/").append(newFileName).toString();
                            // 验证是否是图片
                            String imgExt = ".jpg|.jpeg|.png|.bmp|.GIF|.JPG|.PNG|.JPEG";
                            InputStream ossStream;
                            if (filename.endsWith(imgExt)) {
                                // 创建字节数组输出流
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                // 压缩文件
                                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.8f).toOutputStream(byteArrayOutputStream);
                                // 上传流
                                ossStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                                // 关闭流
                                byteArrayOutputStream.close();
                            } else {
                                ossStream = file.getInputStream();
                            }
                            // 上传操作
                            fileUploadContext.uploadFile(implClassName, ossStream, newFileName, isPrivate);
                            // 是否是私有连接，如果是，返回加密链接
                            if (isPrivate) {
                                pathImg.add(fileUploadContext.getFileUrl(implClassName, newFileName, true));
                            } else {
                                pathImg.add(newFileName);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return pathImg;
        }
        return pathImg;
    }

    /**
     * @author: liushilei
     * @Date: 2020/7/21 13:25
     * @Description: 文件上传方法
     */
    public static List<FileInfo> uploadFileInfo(FileUploadContext fileUploadContext, MultipartFile[] files, String implClassName, boolean isPrivate) {
        List<FileInfo> pathImg = new ArrayList<>(5);
        try {
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];

                    if (file != null) {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileSize(GetFileSize(file.getSize()));
                        // 获取源文件名
                        String filename = file.getOriginalFilename();
                        // 判断文件是否上传
                        if (filename != null && filename.length() > 0) {
                            // 新文件名称
                            String newFileName = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
                            newFileName = new StringBuilder("hryfile/").append(newFileName).toString();
                            // 验证是否是图片
                            String imgExt = ".jpg|.jpeg|.png|.bmp|.GIF|.JPG|.PNG|.JPEG";
                            InputStream ossStream;
                            if (filename.endsWith(imgExt)) {
                                // 创建字节数组输出流
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                // 压缩文件
                                Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.8f).toOutputStream(byteArrayOutputStream);
                                // 上传流
                                ossStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                                // 关闭流
                                byteArrayOutputStream.close();
                            } else {
                                ossStream = file.getInputStream();
                            }
                            // 上传操作
                            fileUploadContext.uploadFile(implClassName, ossStream, newFileName, isPrivate);
                            fileInfo.setFileName(filename);
                            fileInfo.setFilePath(newFileName);
                            fileInfo.setFileType(filename.substring(filename.lastIndexOf(".")));

                        }
                        pathImg.add(fileInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return pathImg;
        }
        return pathImg;
    }

    public static String getFilePath(FileUploadContext fileUploadContext,String implClassName,  String fileKey, boolean isPrivate){
        return fileUploadContext.getFileUrl(implClassName, fileKey, isPrivate);
    }


    private static String GetFileSize(long fileS) {
        String size = "";
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileS < 1024) {
            size = df.format((double) fileS) + "Btye";
        } else if (fileS < 1048576) {
            size = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            size = df.format((double) fileS / 1048576) + "MB";
        } else {
            size = df.format((double) fileS / 1073741824) + "GB";
        }
        return size;
    }
}
