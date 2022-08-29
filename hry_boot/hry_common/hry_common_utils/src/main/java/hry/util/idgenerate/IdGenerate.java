/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0 
 * @Date:        2016年4月1日 下午1:26:36
 */
package hry.util.idgenerate;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * ID生成器
 * <p> TODO</p>
 * @author:         Liu Shilei 
 * @Date :          2016年4月1日 下午1:26:36 
 */
public class IdGenerate {
	
	/** 
	  * 将数据前补零，补后的总长度为指定的长度，以字符串的形式返回 
	  * @param sourceDate 
	  * @param formatLength 
	  * @return 重组后的数据 
	  */  
	 public static String frontCompWithZore(int sourceDate,int formatLength)  
	 {  
	  /* 
	   * 0 指前面补充零 
	   * formatLength 字符总长度为 formatLength 
	   * d 代表为正数。 
	   */  
	  String newString = String.format("%0"+formatLength+"d", sourceDate);  
	  return  newString;  
	 } 
	 
	 /**
	  * 生成银行账户号
	  * 
	  * 生成规则说明  
	  * 生成16位ID
	  * 前缀加 +  8位客户ID的前两位 + 依次加客户后6位第2位 +6位随机数第1位 + 客户后6位第2位 + 5位随机数第2位   直至最后
	  * 例
	  *   前缀88
	  *   客户id  1   格式化00000001
	  *   随机数   123456
	  *   最终ID 8800010203040516
	  * 
	  * 
	  * <p> TODO</p>
	  * @author:         Liu Shilei
	  * @param:    @param customerId
	  * @param:    @param prefix
	  * @param:    @return
	  * @return: String 
	  * @Date :          2016年4月1日 下午1:34:00   
	  * @throws:
	  */
	 public static String accountNum(int customerId,String prefix){
		 String idfromat = frontCompWithZore(customerId, 8);
		 String randomStr = RandomStringUtils.random(6, false, true);
		  
		 String accountNum = prefix;
		 accountNum += idfromat.charAt(0);
		 accountNum += idfromat.charAt(1);
		 
		 for(int i = 2 ; i < idfromat.length() ; i++){
			 accountNum += idfromat.charAt(i);
			 accountNum += randomStr.charAt(i-2);
		 }
		 
		 return accountNum;
	 }
	 
	 /**
	  * 交易订单号
	  * 
	  * 时间戳加3位
	  * 
	  * <p> TODO</p>
	  * @author:         Liu Shilei
	  * @param:    @return
	  * @return: String 
	  * @Date :          2016年4月5日 下午4:25:34   
	  * @throws:
	  */
	 public static String transactionNum1(){
		 SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		 String time = format.format(new Date(System.currentTimeMillis()));
		 String randomStr = RandomStringUtils.random(3, false, true);
		 return time+randomStr;
	 }
	/**
	 * 
	 * <p> 
	 * 00默认
	 * </p>
	 * @author:         Gao Mimi
	 * @param:    @param bussType
	 * @param:    @return
	 * @return: String 
	 * @Date :          2016年5月10日 上午11:55:23   
	 * @throws:
	 */
	 public static String transactionNum(String bussType){
		 SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
		 String time = format.format(new Date(System.currentTimeMillis()));
		 String randomStr = RandomStringUtils.random(4, false, true);
		 if(null==bussType){
			 bussType="00";
		 }
		 return bussType+time+randomStr;
	 }
}
