package hry.security.jwt;


public class JWTToken {

    public final static String TYPE_MANAGE = "manage";//管理端用户
    public final static String TYPE_CUSTOMER = "customer";//前端用户

    public final static String SOURCE_PC = "pc";//pc端
    public final static String SOURCE_APP = "app";//app端

    // 密钥
    private String token;


    public JWTToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


    /**
     * 获取token类型
     *
     * @return
     */
    public String getType() {
        return JWTUtil.getType(token);
    }


    /**
     * 获取用户名
     *
     * @return
     */
    public String getUserName() {
        return JWTUtil.getUsername(token);
    }

    /**
     * 获取token来源
     *
     * @return
     */
    public String getSource() {
        return JWTUtil.getClaim(token, "source");
    }

    /**
     * 获取签名随机id
     *
     * @return
     */
    public String getSignId() {
        return JWTUtil.getClaim(token, "signId");
    }

}

