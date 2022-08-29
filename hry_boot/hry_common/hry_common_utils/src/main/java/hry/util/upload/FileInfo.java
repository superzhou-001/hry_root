package hry.util.upload;

public class FileInfo {

    private String fileName; //文件名称
    private String filePath; //文件路径
    private String fileType; //文件类型
    private String fileSize; //文件大小
    private String fileViewPath;//文件显示路径

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileViewPath() {
        return fileViewPath;
    }

    public void setFileViewPath(String fileViewPath) {
        this.fileViewPath = fileViewPath;
    }
}
