package hry.util.http;

import hry.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * <p>
 * TODO
 * </p>
 *
 * @author: Shangxl
 * @Date : 2017年9月12日 下午4:12:50
 */
@Slf4j
public class Httpclient {
	/**
	 *
	 * <p>
	 * TODO
	 * </p>
	 *
	 * @author: Shangxl
	 * @param: @param url
	 * @param: @param map
	 * @return: void
	 * @Date : 2017年9月12日 下午4:13:08
	 * @throws:
	 */
	public static String post(String url, Map<String, String> map) {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String params = StringUtil.string2params(map);
		// 创建httppost
		HttpPost httppost = new HttpPost(url + "?" + params);
		// 创建参数队列
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			log.info("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity, "UTF-8");
					log.info("Response content: " + result);
					return result;
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 发送 get请求
	 */
	public static String get(String url, Map<String, String> map) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			String params = StringUtil.string2params(map);
			HttpPost httpget = new HttpPost(url + "?" + params);
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
//					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					String result=EntityUtils.toString(entity);
					return result;
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String address = "0xF3b9E9827Eb248280Ba1EDc7972928eb63c3a77b";
		String apiKey = "I71Y7CCH8WQBFT4F99T6NTG2D3UG7EIA2F";
		String url = "http://ropsten.etherscan.io/api";
		Map<String, String> map = new HashMap<String, String>();
		map.put("module", "account");
		map.put("action", "txlist");
		map.put("startblock", "0");
		map.put("endblock", "99999999");
		map.put("sort", "asc");
		map.put("apikey", apiKey);
		map.put("address", address);
		System.out.println(post(url, map));
	}
}
