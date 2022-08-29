package hry.util.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @author: liuchenghui
 *  @Date: 2020/3/27 17:00
 *  @Description: 文件上传环境角色
 */
@Service
public class FileUploadContext {


    private final Map<String, FileUploadStrategy> eStrategyMap = new ConcurrentHashMap<>();

    @Autowired
    public FileUploadContext (Map<String, FileUploadStrategy> eStrategyMap) {
        this.eStrategyMap.clear();
        eStrategyMap.forEach((k, v) -> this.eStrategyMap.put(k, v));
    }
    /**
     *  @author: liuchenghui
     *  @Date: 2020/3/27 17:03
     *  @Description: 文件上传
     */
    public void uploadFile (String thirdType, InputStream inputStream, String objectName, boolean isPrivate) {
        eStrategyMap.get(thirdType).uploadFile(inputStream, objectName, isPrivate);
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/3/27 17:03
     *  @Description: 获取文件上传路径
     */
    public String getFileUrl (String thirdType, String objectName, boolean isPrivate) {
        return eStrategyMap.get(thirdType).getFileUrl(objectName, isPrivate);
    }
}
