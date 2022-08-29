package hry.util.message;

/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Zhang Xiaofang
 * @version:      V1.0 
 * @Date:        2016年4月12日 下午4:37:21
 */


/**
 * <p> TODO</p>
 * @author:         Zhang Xiaofang
 * @Date :          2016年4月12日 下午4:37:21 
 */
public class MessageConstant {
	//required 必须的 不能为空的
	public static final String REQUIRED_ARG="required.arg";//{0}不能为空
	public static final String REQUIRED_ID="required.id";//id不能为空
	public static final String REQUIRED_AUTHORITY="required.authority";//权限不能为空
	public static final String REQUIRED_REGMONEY="required.rechargeMoney";//充值金额不能为空
    public static final String REQUIRED_WITHDRAWMONEY="required.withdrawMoney";//提现金额不能为空
    public static final String REQUIRED_ROLE="required.role";//角色不能为空
    public static final String REQUIRED_COMPANY="required.company";//所属公司不能为空
    public static final String REQUIRED_DEPARTMENT="required.department";//所属部门不能为空
    public static final String REQUIRED_ROLE_ACCNUMBER="required.role.accnumber";//{0}角色已有账号，请先删除账号
    public static final String REQUIRED_NAMEPWD="required.namepwd";//用户名和密码不能为空
    public static final String REQUIRED_registCode="required.registCode";//验证码不能为空
    public static final String REQUIRED_mobilePhone="required.mobilePhone";//手机号不能为空
    
    //=
    //exist  已经存在的
    public static final String EXIST_ARG="exist.arg";//{0}已经存在
    public static final String EXIST_ACCOUNT="exist.account";//此账户已存在
    public static final String EXIST_COINCODE="exist.coinCode";//币的代码已存在

    //=
    
    //noExist  不存在的
    public static final String NOEXIST_ARG="noExist.arg";//{0}不存在
    public static final String NOEXIST_VACCOUNT="noExist.virtualAccount";//虚拟账户不存在
    
    
    
    //format  格式类的提示
    public static final String FORMAT_ARG="format.arg";//{0}格式不正确
    public static final String FORMAT_COIN="format.coin";//输入的币的格式不正确

    
    
    //incorrect  不正确的  
    public static final String INCORRECT_ARG="incorrect.arg";//{0}不正确
    public static final String INCORRECT_ZJPWD="incorrect.zjPWD";//原始资金密码不正确
    public static final String INCORRECT_PWD="incorrect.pwd";//原始密码不正确
    public static final String INCORRECT_REGACCOUNT="incorrect.rechargeAccout";//充值账户不正确
    public static final String INCORRECT_CODE="incorrect.code";//验证码不正确
    public static final String INCORRECT_RECOMMENDCODE="推荐码不存在或者错误";//推荐码不正确
                                         
    
    
    //different  不一致的
    public static final String DIFFERENT_ARG="different.arg";//原始密码不正确
    public static final String DIFFERENT_PWD="different.pwd";//密码不一致请修改
    


    //timeout  超时
    public static final String TIMEOUT_ARG="timeout.arg";//{0}超时
    public static final String TIMEOUT_PAGE="timeout.page";//页面超时
    
    
    
    //illegal  非法
    public static final String ILLEGAL_ARG="illegal.arg";//{0}非法
    public static final String ILLEGAL_ORGANIZEADD="illegal.organize.add";//添加非法，权限列表为空
    
    
    
    //error 错误
    public static final String ERROR_ARG="error.arg";//{0}错误
    public static final String ERROR_NAMEPWD="error.name_pwd";//用户名/密码错误
    public static final String ERROR_PAYPWD="error.payPWD";//支付密码错误
    public static final String ERROR_CODE="error.code";//验证码错误
    public static final String ERROR_registSmsCode="error.registSmsCode";//短信验证码错误
    public static final String ERROR_DEL_DEPART="error.del.department";//删除部门出错
    public static final String ERROR_DEL_ROLE="error.del.role";//删除角色出错
    public static final String ERROR_SELECT_ACCOUNT="error.select.account";//查询用户的虚拟账户出问题
    //=
   
    
    //exception  异常
    public static final String EXCEPTION_ARG="exception.arg";//{0}异常
    public static final String EXCEPTION_ADD="exception.add";//增加异常
    
    
    
    
    //failed  失败
    public static final String FAILED_ARG="failed.arg";//{0}失败
    public static final String FAILED_UPLOAD="failed.upload";//上传图片失败
    public static final String FAILED_UPDATE="failed.update";//更新失败
    public static final String FAILED_MODIFY="failed.modify";//修改失败
    public static final String FAILED_NEWACCOUNT="failed.newAccount";//新账户提交失败
    public static final String FAILED_BID="failed.bid";//投标失败
    public static final String FAILED_SAVEORDER="failed.saveOrder";//保存订单失败
    public static final String FAILED_REG="falied.reg";//注册失败
    public static final String FAILED_REMOTE_CONNECTION="failed.remoteConnection";//远程连接失败
    public static final String FAILED_WITHDRAW_APPLY="failed.withdrawApply";//提现申请失败

    
    
    
    //success  成功
    public static final String SUCCESS_ARG="success.arg";//{0}成功
    public static final String SUCCESS="success";//成功
    public static final String SUCCESS_REG="success.reg";//注册成功
    public static final String SUCCESS_BID="success.bid";//投标成功
    public static final String SUCCESS_DEL="success.del";//删除成功
    public static final String FAILED_DEL="failed.del";//删除失败
    public static final String SUCCESS_OPEN="success.open";//开通成功
    public static final String SUCCESS_UPDATE="success.update";//更新成功
    public static final String SUCCESS_NEWACCOUNT="success.newAccount";//新账户已提交
    public static final String SUCCESS_WITHDRAW_APPLY="success.withdrawApply";//提现申请成功
    
    //=
    
    //repeat 重复            
    public static final String REPEAT_ARG="repeat.arg";//{0}重复
    public static final String REPEAT_ACCOUNT="repeat.account";//账户重复请修改
    public static final String REPEAT_NUMBER="repeat.number";//账号不能重复
    public static final String REPEAT_LOGIN="repeat.login";//已经登录了
    public static final String REPEAT_OPEN="repeat.open";//已经开通过了

    
    
    
    //please 比较友好的提示信息               
    public static final String PLEASE_INFO="please.inputInfo";//请补填基本信息
    public static final String PLEASE_LOGIN="please.login";//请先登录
    public static final String PLEASE_COINTYPE="please.coinType";//请填写正确的提币类型
    public static final String PLEASE_OPEN="please.openAccount";//请先开通业务平台账号

    
    
    
    //out                                
    public static final String OUT_LOGIN="out.login";//您已经被管理员强制退出，请重新登录
    
    
   
    
    //more                   
    public static final String MORE_LOGIN="more.login";//登录次数过多,请{0}分钟后再试
    
    
    
    //wait                       
    public static final String WAIT_ORDER="wait.order";//订单已提交请耐心等待
    
    
    
    //over                       
    public static final String OVER_WITHDRAWMONEY="over.withdrawMoney";//提现的金额超出了最大可用余额
   
    //over 
    public static final String OVER_WITHDRAWNUM="over.withdrawNum";//提现的数量超出了最大可用余额
    
    
    //less  
    public static final String LESS_BALANCE="less.balance";//余额不足

    
    
    //no                             
    public static final String NO_REALNAME="no.realName";//用户没有实名认证
    public static final String NO_LOGIN="no.login";//用户没登录
    public static final String NO_COINTYPE="no.coinType";//数据库中没有这样币种

    
//    邮件的配置 
//    PropertiesUtils.APP.getProperty("app.saasId");
  
//    public static final String Mail_SERVER = "smtp.126.com"; //邮件服务器
//    public static final String Mail_DEAL = "smtp"; //发送协议
//    public static final String Mail_ISTRUE = "true"; //同时通过认证(建议true)
//    public static final String Mail_USER = "wu_shuiming"; //认证用户名
//    public static final String Mail_PASSWORD = "mike123456789"; //认证密码
//    public static final String Mail_SENDUSERNAME = "wu_shuiming@126.com"; //显示用户名
  
    
    //lock 锁定
    public static final String LOCK_CUSTOMER = "lock.customer"; //用户已经被锁定，无法登陆
         
    
    //forbidden 禁用
    public static final String FORBIDDEN_CUSTOMER = "forbidden.customer"; //用户已经被禁用，无法登陆

  //test 测试
    public static final String TEST = "test"; 
}
