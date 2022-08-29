package hry.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;
import java.util.UUID;

public class JWTUtil {

    // token过期时间1天，秒
    public static final int EXPIRE_TIME = 24*60*60;

    // token刷新时间7天 单位秒
    public static final int REFRESH_TIME = 7*24*60*60;


    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @param singId 签名id
     * @param source 来源，电脑(pc)还是手机端(app)
     * @param type 类型，前台客户(customer)还是后台管理用户(manage)
     * @return 是否正确
     */
    public static boolean verify(String token,String singId, String username, String source,String type, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(username)
                    .withClaim("source", source)
                    .withClaim("type", type)
                    .withClaim("signId", singId)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getIssuer();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/7 16:54
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
//            System.out.println("获取"+claim+"失败,token错误");
            //e.printStackTrace();
        }
        return null;
    }
    /**
     * 生成签名
     * @param username 用户名
     * @param secret 用户的密码
     * @param source 来源，电脑(pc)还是手机端(app)
     * @param type 类型，前台客户(customer)还是后台管理用户(manage)
     * @return 加密的token
     */
    public static String sign(String username, String source,String type,String secret) {
        try {
            // 签发时间
            Date iatDate = new Date();
            Date date = new Date(iatDate.getTime() + EXPIRE_TIME*1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withIssuer(username)
                    .withClaim("source", source)
                    .withClaim("type", type)
                    .withClaim("signId", getUUID())
                    .withExpiresAt(date)
                    .withIssuedAt(iatDate)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    private static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    public static void main(String[] args) throws InterruptedException {
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        String token = sign("e65@qq.com", "pc",JWTToken.TYPE_MANAGE, "hryToken");
        String token2 = sign("e65@qq.com", "pc",JWTToken.TYPE_MANAGE, "hryToken");
        System.out.println("token="+token);
        System.out.println("token2="+token2);
        System.out.println("是否相等"+token.equals(token2));
        while (true) {
            Thread.sleep(1000L);
            String username = getUsername(token);
            String username2 = getUsername(token2);
            if(verify(token, username, "","pc",JWTToken.TYPE_MANAGE, "hryToken")){
                System.out.println(username+true);
            } else {
                System.out.println(false);
                token = sign("e65@qq.com", "pc", JWTToken.TYPE_MANAGE,"hryToken");
                System.out.println(token);
            }
        }
    }

    /**
     * 获取token类型
     * @param token
     * @return
     */
    public static String getType(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim("type").asString();
        } catch (JWTDecodeException e) {
//            System.out.println("获取jwt-type失败,token错误");
        }
        return null;
    }


    /**
     * 签名随机id
     * 获取signId
     * @param token
     * @return
     */
    public static String getSignId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim("signId").asString();
        } catch (JWTDecodeException e) {
//            System.out.println("获取jwt-type失败,token错误");
        }
        return null;
    }


    public static String getManageRefreshTimeKey(String token){
        return "JWT:token:"+JWTToken.SOURCE_PC+":manage:refreshTime:" +getSignId(token);
    }

    public static String getManageUserKey(String token) {
        return "JWT:token:"+JWTToken.SOURCE_PC+":manage:user:" +getSignId(token);
    }

    public static String getManageShiroUrlsKey(String token) {
        return "JWT:token:"+JWTToken.SOURCE_PC+":manage:shiroUrls:" +getSignId(token);
    }

    public static String md5Id(String token){
       return DigestUtils.md5Hex(getUsername(token));
    }


    public static String getCustomerRefreshTimeKey(String token){
        return "JWT:token:"+JWTToken.SOURCE_PC+":customer:refreshTime:" +getSignId(token);
    }

    public static String getCustomerUserKey(String token) {
        return "JWT:token:"+JWTToken.SOURCE_PC+":customer:user:" +getSignId(token);
    }

    //全部登录的token
    public static String getManageAllKey() {
       return  "JWT:token:"+JWTToken.SOURCE_PC+":manage:allToken";
    }
}
