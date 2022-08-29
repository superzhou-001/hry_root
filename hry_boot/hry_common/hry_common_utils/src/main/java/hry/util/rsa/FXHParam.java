package hry.util.rsa;

/*
    请求非小号参数
 */
public class FXHParam {
    private String companyCode;
    private String payUrl;
    private String privateKey;
    private String publicKey;
    private String coinCode;
    private String fixCoin;
    private String startTime; // 开始时间
    private String endTime; // 结束时间
    private String phone;   //发送短信手机号
    private String msg;  //短信详细信息
    private String SignName; //短信签名 无须加【】等字符
    private String TemplateCode; //模板
    private String templateParam; //短信模板变量替换JSON串
    private String ordernumber; //订单号
    private String userName;
    private String cardId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSignName() {
        return SignName;
    }

    public void setSignName(String signName) {
        SignName = signName;
    }

    public String getTemplateCode() {
        return TemplateCode;
    }

    public void setTemplateCode(String templateCode) {
        TemplateCode = templateCode;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }

    public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public FXHParam() {
    }

    public FXHParam(String companyCode, String payUrl, String privateKey, String publicKey, String coinCode, String fixCoin) {
        this.companyCode = companyCode;
        this.payUrl = payUrl;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.coinCode = coinCode;
        this.fixCoin = fixCoin;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getFixCoin() {
        return fixCoin;
    }

    public void setFixCoin(String fixCoin) {
        this.fixCoin = fixCoin;
    }
}
