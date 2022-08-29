/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Yuan Zhicheng
 * @version:      V1.0 
 * @Date:        2015年9月16日 上午11:04:39
 */
package hry.util.httpRequest;

import javax.servlet.http.HttpServletRequest;

import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.json.Json;
import org.nutz.lang.hardware.Networks;
import org.nutz.mapl.Mapl;
import org.springframework.util.StringUtils;

/**
 * IP工具类
 * 
 * @author:      Yuan Zhicheng
 *
 */
public class IpUtil {

	/**
	 * 获得IP地址
	 * 
	 * @return
	 */
	public static String getIp() {
		return Networks.ipv4();
	}

	/**
	 * 获得IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    
	}

	/**
	 * 通过IP获得地址信息，调用的是淘宝的IP接口
	 * 
	 * @param ip
	 * @return
	 */
	public static String getAddr(String ip) {
		String info = "";
		Response resp = Http.get("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
		if (resp.isOK()) {
			String result = resp.getContent();
			Object obj = Json.fromJson(result);
			if (Mapl.cell(obj, "code").toString().equals("0")) {
				info += Mapl.cell(obj, "data.country") + " ";
				info += Mapl.cell(obj, "data.region") + " ";
				info += Mapl.cell(obj, "data.city") + " ";
				info += Mapl.cell(obj, "data.isp") + " ";
			}
		}
		return info;
	}
	public static boolean  isMobileDevice(String requestHeader){
        /**
         * android : 所有android设备
         * mac os : iphone ipad
         * windows phone:Nokia等windows系统的手机
         */
        String[] deviceArray = new String[]{"android","mac os","windows phone"};
        if(requestHeader == null)
            return false;
        requestHeader = requestHeader.toLowerCase();
        for(int i=0;i<deviceArray.length;i++){
            if(requestHeader.indexOf(deviceArray[i])>0){
                return true;
            }
        }
        return false;
}
}
