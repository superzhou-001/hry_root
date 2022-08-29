
package hry.util.file;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    private static final String TAG = Md5Util.class.getName();
    private static MessageDigest mMessageDigest;
    private static char mHexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    static {
        try {
            mMessageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            //Log.e("", TAG + " MessageDigest.getInstance() failed :" + e.toString());
        }
    }
    
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileMD5String(String filePath) throws IOException {
        InputStream fis;
        fis = new FileInputStream(filePath);
        byte[] buf = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buf)) != -1) {
            mMessageDigest.update(buf, 0, numRead);
        }
        fis.close();
        return bufferToHex(mMessageDigest.digest());
    }
    
    public static String getFileMD5String(InputStream fis) throws IOException {
       
        byte[] buf = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buf)) != -1) {
            mMessageDigest.update(buf, 0, numRead);
        }
        fis.close();
        return bufferToHex(mMessageDigest.digest());
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringBuffer) {
        char c0 = mHexDigits[(bt & 0xf0) >> 4]; 
        char c1 = mHexDigits[bt & 0xf]; 
        stringBuffer.append(c0);
        stringBuffer.append(c1);
    }
    
    /**
     * baidu
     * md5加密
     * <p> TODO</p>
     * @author:         Shangxl
     * @param:    @param plainText
     * @param:    @return
     * @param:    @throws NoSuchAlgorithmException
     * @return: String 
     * @Date :          2017年7月26日 下午2:47:45   
     * @throws:
     */
    public static String encryption(String plainText) throws NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("md5");  
        byte[] cipherData = md5.digest(plainText.getBytes());  
        StringBuilder builder = new StringBuilder();  
        for(byte cipher : cipherData) {
            String toHexStr = Integer.toHexString(cipher & 0xff);  
            builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);  
        }  
    	return builder.toString();
    }
    
}
