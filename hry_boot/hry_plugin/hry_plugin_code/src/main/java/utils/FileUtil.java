/**
 * Copyright:   互融云
 * @author:      Yuan Zhicheng
 * @version:      V1.0
 * @Date:        2015年9月16日 上午11:04:39
 */
package utils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * <p> 文件基本工具类  </p>
 * @author:         Gao Mimi
 * @Date :          2015年10月10日 下午2:27:27
 */
public class FileUtil {
	/**
	 * 16K
	 */
	private static final int BUFFER_SIZE = 16*1024;

	/**
	 * 菜单备份路径
	 */
	public static final String STANDBY = "/backup/menu/standby";



	/**
	 * 获取文件扩展名
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 获取不带扩展名的文件名
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}




	/**把 src 文件 复制 到 dst去*/
	public static void fileUpload(File src, File dst){

		try {
			InputStream in = null;
			OutputStream out = null;

			try {
				in = new BufferedInputStream(new FileInputStream(src));
				out = new BufferedOutputStream(new FileOutputStream(dst));

				byte[] buffer = new byte[BUFFER_SIZE];

				while(in.read(buffer) > 0){
					out.write(buffer);
				}
			}finally{
				if(null != in){
					in.close();
				}
				if(null != out){
					out.close();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 根据路径创建文件夹
	 *
	 *
	 */
	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
//			file = null;
		}
		return false;
	}







	/**
	 * 判断文件名是否已经存在，如果存在则在后面家(n)的形式返回新的文件名，否则返回原始文件名 例如：已经存在文件名 log4j.htm
	 * 则返回log4j(1).htm
	 */
	public static String checkFileName(String fileName, String dir) {
		boolean isDirectory = new File(dir + fileName).isDirectory();
		if (FileUtil.isFileExist(fileName, dir)) {
			int index = fileName.lastIndexOf(".");
			StringBuffer newFileName = new StringBuffer();
			String name = isDirectory ? fileName : fileName.substring(0, index);
			String extendName = isDirectory ? "" : fileName.substring(index);
			int nameNum = 1;
			while (true) {
				newFileName.append(name).append("(").append(nameNum).append(")");
				if (!isDirectory) {
					newFileName.append(extendName);
				}
				if (FileUtil.isFileExist(newFileName.toString(), dir)) {
					nameNum++;
					newFileName = new StringBuffer();
					continue;
				}
				return newFileName.toString();
			}
		}
		return fileName;
	}
   /**
    * 判断文件是否存在
    *
    */
	public static boolean isFileExist(String fileName, String dir) {
		File files = new File(dir + fileName);
		return files.exists();
	}
	/**
	 * 判断文件是否存在
	 *
	 */
	public static boolean isFileExist(String file) {
		File files = new File(file);
		return files.exists();
	}
	/**
	 * 移动文件,不可以移动文件家
	 * @param src 源文件
	 * @param dest 目标文件
	 */
	public static void moveFile(String src, String dest) {
	new File(src).renameTo(new File(dest));
	}
	/**
	 * 获取文件内容
	 *
	 * @param src  源文件
	 * @return String[] 文件内容数组，每行占一个数组空间
	 * @throws IOException
	 */
	public static String[] readContent(String src, Charset charset)throws IOException {
		FileReader reader = new FileReader(src);
		BufferedReader br = new BufferedReader(reader);
		String[] array = new String[readLineNumber(src)];
		String line;
		int lineNumber = 0;
		while ((line = br.readLine()) != null) {
		array[lineNumber] = line;
		lineNumber++;
		}
		reader.close();
		br.close();
		return array;
	}
	/**
	 * 获取文件行数
	 * @param src 源文件
	 * @return int 内容行数
	 * @throws IOException
	 */
	public static int readLineNumber(String src) throws IOException {
		FileReader reader = new FileReader(src);
		BufferedReader br = new BufferedReader(reader);
		int lineNumber = 0;
		while (br.readLine() != null) {
		lineNumber++;
		}
		reader.close();
		br.close();
		return lineNumber;
	}
	/**
	 * 重命名文件||实际上调用本类的moveFile方法
	 * @param src   源文件
	 * @param dest   目标文件
	 *
	 */
	public static void renameFile(String src, String dest) {
	   moveFile(src, dest);
	}

	/**
	 * 把数据写至文件中
	 * @param filePath
	 * @param data
	 */
	public static void writeFile(String filePath,String data){
		FileOutputStream fos = null;
		OutputStreamWriter writer=null;
		try {
			fos = new FileOutputStream(new File(filePath));
			writer=new OutputStreamWriter(fos, "UTF-8");
			writer.write(data);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				if(writer!=null){
					writer.close();
				}
				if (fos != null){
					fos.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 读取文件内容
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath){
		 StringBuffer buffer = new StringBuffer();
		// 读出这个文件的内容
		try{
			File file = new File(filePath);
		    FileInputStream fis = null;
		    BufferedReader breader=null;
		    try {
		      fis = new FileInputStream(file);
		      InputStreamReader isReader=new InputStreamReader(fis,"UTF-8");
		      breader=new BufferedReader(isReader);
		      String line;
		      while((line=breader.readLine())!=null) {
		        buffer.append(line);
		        buffer.append("\r\n");
		      }
		      breader.close();
		      isReader.close();
		      fis.close();

		    } catch (FileNotFoundException e) {
		      System.out.println(e.getMessage());
		    } catch (IOException e) {
		    	System.out.println(e.getMessage());
		    }
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return buffer.toString();
	}
	/**
	 * 创建文件
	 * @param filePath
	 * @param filename
	 * @return
	 */
	public static void createFile(String filePath,String filename){

		 File dir=new File(filePath);
		 if(!dir.exists()){
			 dir.mkdirs();
		 }
		 File file=new File(filename);
		 try {
			 if(!file.exists()){
				 file.createNewFile();
			 }

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除webInfo/class
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @return
	 * @return: boolean
	 * @Date :          2015年10月19日 下午2:04:23
	 * @throws:
	 */
	public static boolean deleteFile(String filePath){
		File file = new File(filePath);
		if(file!=null){
			file.delete();
			return true;
		}
		return false;
	}

	/**
	 * 创建文件
	 * 	文件夹不存在也可以自动创建文件夹
	 * <p> TODO</p>
	 * @author:         Liu Shilei
	 * @param:    @param destFileName   例：/back/menu/menu.xml
	 * @param:    @return
	 * @return: boolean
	 * @Date :          2015年11月6日 上午11:53:03
	 * @throws:
	 */
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if(!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }

	 /**
	  * 读取远程文件转字符串
	  * <p> TODO</p>
	  * @author:         Liu Shilei
	  * @param:    @return
	  * @return: String
	  * @Date :          2015年11月6日 上午11:55:59
	  * @throws:
	  */
	 public static  String readRemoteFile2String(String fileURL){
		 InputStream is  = null;
		 ByteArrayOutputStream   bs = null;
		 try {

			 URL url = new URL(fileURL);
	         is = url.openStream();
	         StringBuffer   out   =   new   StringBuffer();
	         byte[]   b   =   new   byte[4096];
	         for(int   n;   (n   =   is.read(b))   !=   -1;)   {
	             out.append(new   String(b,   0,   n));
	         }
	         return   out.toString();


		 } catch (Exception e) {
				e.printStackTrace();
		 }finally{
			 try {
				 if(is!=null){
					 is.close();
				 }
				 if(bs!=null){
					 bs.close();
				 }
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		 }
		 return null;

	 }





}
