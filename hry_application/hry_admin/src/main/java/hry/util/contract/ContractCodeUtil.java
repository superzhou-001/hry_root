package hry.util.contract;

import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * @Author: yaozh
 * @Description:
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
public class ContractCodeUtil {

    public static String getCode(String format){
        String[] strs = format.split("\\$");
        StringBuffer sb = new StringBuffer();
        for (String str : strs) {
            if (StringUtils.isEmpty(str)){
                continue;
            }
            Calendar cal = Calendar.getInstance();
            switch (str){
                case "Y":
                    sb.append(cal.get(Calendar.YEAR));
                    break;
                case "M":
                    sb.append(cal.get(Calendar.MONTH)+1);
                    break;
                case "D":
                    sb.append(cal.get(Calendar.DATE));
                    break;
                case "T":
                    sb.append(cal.get(Calendar.YEAR));
                    sb.append(cal.get(Calendar.MONTH)+1);
                    sb.append(cal.get(Calendar.DATE));
                    break;
                case "C":
                    sb.append("");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + str);
            }

        }

        return sb.toString();
    }

}
