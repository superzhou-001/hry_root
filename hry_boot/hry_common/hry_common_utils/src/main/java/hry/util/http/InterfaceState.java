package hry.util.http;
/**
 * api返回状态码
 * <p> TODO</p>
 * @author:         Shangxl 
 * @Date :          2017年8月8日 下午4:15:36
 */
public class InterfaceState {
	/** 
	 * 200：成功
	 */
	public static final String SUCCESS = "200";
	/** 
	 * 1010：无效的参数
	 */
	public static final String INVALID_PARAMETER = "1010";
	/** 
	 * 1020：权限验证失败
	 */
	public static final String AUTHORIZATION_VALIDATION_FAILED = "1020";
	/** 
	 * 1030：账户未创建
	 */
	public static final String ACCOUNT_NOT_CREATED = "1030";
	/** 
	 * 1040：资金不足
	 */
	public static final String INSUFFICIENT_FUNDS = "1040";
	/** 
	 * 1050：数据库错误
	 */
	public static final String DATABASE_ERROR = "1050";
	/** 
	 * 1060：钱包中存在的账户
	 */
	public static final String ACCOUNT_IN_PURSE = "1060";
	/** 
	 * 1070：账户存在
	 */
	public static final String ACCOUNT_EXISTS = "1070";
	/** 
	 * 1080: 检查失败
	 */
	public static final String CHECK_FAILED = "1080";
	/** 
	 * 1090：没有验证码
	 */
	public static final String NO_VALIDATION_CODE = "1090";
	/** 
	 * 1100：余额不足
	 */
	public static final String NOT_SUFFICIENT_FUNDS = "1100";
	/** 
	 * 1110：剩余的错误列表
	 */
	public static final String LIST_OF_REMAINING_ERRORS = "1110";
	/** 
	 * 1200：创建原始交易错误
	 */
	public static final String CREATE_ORIGINAL_TRANSACTION_ERROR = "1200";
	/** 
	 * 1300：签署原始交易错误
	 */
	public static final String SIGN_ORIGINAL_TRANSACTION_ERROR = "1300";
	/** 
	 * 1400: 钱包的转账失败
	 */
	public static final String THE_TRANSFER_OF_THE_WALLET_FAILED = "1400";
	/** 
	 * 1500：钱包错误
	 */
	public static final String WALLET_ERROR = "1500";
	/** 
	 * 1600：短信错误
	 */
	public static final String SMS_ERROR = "1600";
	/** 
	 * 1700：没有日结信息
	 */
	public static final String NO_DATE_INFORMATION = "1700";
	/** 
	 * 1800：日结报错
	 */
	public static final String DAILY_REPORTING = "1800";
	/** 
	 * 1900：推队列误差
	 */
	public static final String PUSH_QUEUE_ERROR = "1900";
	/** 
	 * 2000：TX已存在
	 */
	public static final String TX_ALREADY_EXISTS = "2000";
	/** 
	 * 1021：应用中存在的地址
	 */
	public static final String ADDRESS_IN_APPLICATION = "1021";
	/** 
	 * 1022: 无效的地址
	 */
	public static final String INVALID_ADDRESS = "1022";
	/** 
	 * 500: 服务器错误
	 */
	public static final String  INTERNAL_SERVER_ERROR="500";
}
