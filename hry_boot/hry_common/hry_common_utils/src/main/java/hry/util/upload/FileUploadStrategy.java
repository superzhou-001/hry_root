package hry.util.upload;

import java.io.InputStream;

/**
 *  @author: liuchenghui
 *  @Date: 2020/3/27 16:54
 *  @Description: 文件上传策略类
 */
public interface FileUploadStrategy {

    /**
     *  @author: liuchenghui
     *  @Date: 2020/3/27 16:57
     *  @Description: 上传文件
     */
    public void uploadFile (InputStream inputStream, String objectName, boolean isPrivate);

    /**
     *  @author: liuchenghui
     *  @Date: 2020/3/27 16:57
     *  @Description: 获取文件路径
     */
    public String getFileUrl (String objectName, boolean isPrivate);
}
