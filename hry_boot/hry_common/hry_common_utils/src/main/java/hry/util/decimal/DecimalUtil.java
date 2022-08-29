/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0 
 * @Date:        2015年12月7日 下午3:26:31
 */
package hry.util.decimal;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * BigDecimal 处理工具类
 * <p>
 * TODO
 * </p>
 * 
 */
public class DecimalUtil {

	/**
	 * BigDecimal类型去除多余的小数点和小数点后的0
	 */
	public static BigDecimal getPrettyNumber(String dec){
        BigDecimal decimal = new BigDecimal(BigDecimal.valueOf(Double.parseDouble(dec)).stripTrailingZeros().toPlainString());
        return decimal;
    }

	

}
