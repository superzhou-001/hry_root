/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Zhang Xiaofang
 * @version:      V1.0
 * @Date:        2016年9月14日 上午10:43:57
 */
package hry.util.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * <p>
 * TODO
 * </p>
 *
 * @author: Zhang Xiaofang
 * @Date : 2016年9月14日 上午10:43:57
 */
@Slf4j
public class HttpsRequest {

	// 请求https:
	public static String post(String url) {
		// 增加下面两行代码
		Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
		Protocol.registerProtocol("https", myhttps);
		HttpClient client = new HttpClient();
		//连接时间
		client.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
		//读取超时
		client.getHttpConnectionManager().getParams().setSoTimeout(20000);

		HttpMethod post = new PostMethod(url);
		try {
			client.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String result = new String(responseBody, "UTF-8");
			return result;
		} catch (Exception e) {
			log.error("远程调用失败:【调用地址="+url+"】");
			post.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		} finally {
			post.releaseConnection();
			client.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}
	/**
	 * http post请求
	 * <p> TODO</p>
	 * @author:         Shangxl
	 * @param:    @param strUrl
	 * @param:    @param param
	 * @param:    @return
	 * @return: String
	 * @Date :          2017年7月27日 下午3:24:47
	 * @throws:
	 */
	public static String postSend(String strUrl, String param) {

		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			// POST方法时使用
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(param);
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}


	public static void main(String[] args) {
		System.out.println(HttpsRequest.post("http://admin.kmcoincn.com/admin/app/appcache/update?types=,appConfigService"));// 支付宝做测试
	}
}
