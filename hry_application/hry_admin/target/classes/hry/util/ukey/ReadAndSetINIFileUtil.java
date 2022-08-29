package hry.util.ukey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取和设置ini配置文件工具类
 *
 * @author liuchenghui
 */
public final class ReadAndSetINIFileUtil {

    static Logger logger = LoggerFactory.getLogger(ReadAndSetINIFileUtil.class);


    private static Pattern P = Pattern.compile("\\[\\w+]");
    /**
     * 从ini配置文件中读取变量的值
     *
     * @param file         配置文件的路径
     * @param section      要获取的变量所在段名称
     * @param variable     要获取的变量名称
     * @param defaultValue 变量名称不存在时的默认值
     * @return 变量的值
     * @throws IOException 抛出文件操作可能出现的io异常
     */
    public static String readCfgValue (String file, String section, String variable, String defaultValue) {
        String strLine, value = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            boolean isInSection = false;
            while ((strLine = bufferedReader.readLine()) != null) {
                strLine = strLine.trim();
                if (strLine.startsWith(";")) {
                    strLine = strLine.split("[;]")[0];
                }
                if (strLine.startsWith("#")) {
                    strLine = strLine.split("[#]")[0];
                }
             //   Pattern p;
                Matcher m;
          //      p = Pattern.compile("\\[\\w+]");//Pattern.compile("file://[//s*.*//s*//]");
                m = P.matcher((strLine));
                isInSection = isInSection(P, section, strLine, isInSection, m);
                if (isInSection == true) {
                    strLine = strLine.trim();
                    String[] strArray = strLine.split("=");
                    if (strArray.length == 1) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = "";
                            return value;
                        }
                    } else if (strArray.length == 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = strArray[1].trim();
                            return value;
                        }
                    } else if (strArray.length > 2) {
                        value = strArray[0].trim();
                        if (value.equalsIgnoreCase(variable)) {
                            value = strLine.substring(strLine.indexOf("=") + 1).trim();
                            return value;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * 修改ini配置文件中变量的值
     *
     * @param file     配置文件的路径
     * @param section  要修改的变量所在段名称
     * @param variable 要修改的变量名称
     * @param value    变量的新值
     * @throws IOException 抛出文件操作可能出现的io异常
     */
    public static boolean writeCfgValue (String file, String section, String variable, String value) {
        String fileContent, allLine, strLine, newLine, remarkStr = "";
        String getValue = null;
        BufferedReader bufferedReader = null;
        boolean isInSection = false;
        boolean canAdd = true;
        fileContent = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            while ((allLine = bufferedReader.readLine()) != null) {
                strLine = allLine.trim();
                if (allLine.startsWith(";")) {
                    strLine = allLine.split("[;]")[0];
                }
                if (allLine.startsWith("#")) {
                    strLine = allLine.split("[#]")[0];
                }
                Pattern p;
                Matcher m;
              //  p = Pattern.compile("\\[\\w+]");
                m = P.matcher((strLine));
                isInSection = isInSection(P, section, strLine, isInSection, m);
                if (isInSection == true) {
                    String[] strArray = strLine.split("=");
                    getValue = strArray[0].trim();
                    if (getValue.equalsIgnoreCase(variable)) {
                        newLine = getValue + "=" + value;
                        fileContent += newLine;
                        while ((allLine = bufferedReader.readLine()) != null) {
                            fileContent += "\r\n" + allLine;
                        }
                        bufferedReader.close();
                        canAdd = false;
                        System.out.println(fileContent);
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                        bufferedWriter.write(fileContent);
                        bufferedWriter.flush();
                        bufferedWriter.close();

                        return true;
                    }

                }
                fileContent += allLine + "\r\n";
            }
            if (canAdd) {
                String str = variable + "=" + value;
                fileContent += str + "\r\n";
                //System.out.println(fileContent);
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                bufferedWriter.write(fileContent);
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 判断是否是节点段
     *
     * @param p           Pattern对象
     * @param section     段名称
     * @param strLine     匹配字符串
     * @param isInSection 是否是节点
     * @param m           Matcher对象
     * @return
     */
    private static boolean isInSection (Pattern p, String section, String strLine, boolean isInSection, Matcher m) {
        if (m != null && m.matches()) {
            p = Pattern.compile("\\[" + section + "\\]");
            m = p.matcher(strLine);
            if (m.matches()) {
                isInSection = true;
            } else {
                isInSection = false;
            }
        }
        return isInSection;
    }

    public static void main (String[] args) {
        String path = ReadAndSetINIFileUtil.class.getClassLoader().getResource("").getPath();
        // String file, String section, String variable, String defaultValue
        System.out.println(readCfgValue(path + "ukey/ini/JCryptoWrapperWin.ini", "SETTINGS", "EncryptionKeyFile", ""));
    }
}
