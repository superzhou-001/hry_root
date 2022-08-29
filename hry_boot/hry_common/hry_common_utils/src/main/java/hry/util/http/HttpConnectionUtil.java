/**
 * Copyright:   北京互融时代软件有限公司
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年5月18日 下午3:28:29
 */
package hry.util.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


/**
 * <p> TODO</p>
 * @author: Liu Shilei
 * @Date :          2016年5月18日 下午3:28:29
 */
@Slf4j
public class HttpConnectionUtil {

    public static void main(String[] args) {

        // https://manage.7ebit.com/manage/transaction/exdmcustomerpublickey/list?start=0&length=-1&msid=547db428-6c8e-49ac-98da-541fb2803296
        String send = getSendHttps("https://admin.7ebit.com/admin/transaction/exdmcustomerpublickey/list?start=0&length=-1&msid=547db428-6c8e-49ac-98da-541fb2803296");
        System.out.println(send);

    }


    private static String getSendHttps(String url) {

        // 增加下面两行代码
        Protocol myhttps = new Protocol("https", new MySSLSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttps);
        HttpClient client = new HttpClient();
        //连接时间
        client.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
        //读取超时
        client.getHttpConnectionManager().getParams().setSoTimeout(20000);

        HttpMethod post = new GetMethod(url);
        try {
            client.executeMethod(post);
            byte[] responseBody = post.getResponseBody();
            String result = new String(responseBody, "UTF-8");
            return result;
        } catch (Exception e) {
            log.error("远程调用失败:【调用地址=" + url + "】");
            post.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        } finally {
            post.releaseConnection();
            client.getHttpConnectionManager().closeIdleConnections(0);
        }
        return null;

    }


    /**
     * get请求
     * <p> TODO</p>
     * @author: Liu Shilei
     * @param:    @param strUrl  请求地址
     * @param:    @param param   请求参数
     * @param:    @return
     * @return: String
     * @Date :          2016年5月18日 下午3:32:07
     * @throws:
     */
    public static String getSend(String strUrl, String param) {
        if (strUrl.indexOf("https") != -1 || strUrl.indexOf("HTTPS") != -1) {
            return getSendHttps(strUrl + "?" + param);
        }
        URL url = null;
        HttpURLConnection connection = null;

        try {
            url = new URL(strUrl + "?" + param);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.connect();
            connection.setConnectTimeout(100000);
            connection.setReadTimeout(100000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (ConnectException c) {
            System.out.println("访问" + url + "超时");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }


    /**
     * post请求
     * <p> TODO</p>
     * @author: Liu Shilei
     * @param:    @param strUrl  请求地址
     * @param:    @param param   请求参数
     * @param:    @return
     * @return: String
     * @Date :          2016年5月18日 下午3:32:34
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

            //POST方法时使用
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            //以下语句在转中文时候，已经变成乱码
            //out.writeBytes(param);
            out.write(param.getBytes());
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

    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();

        boolean var4;
        try {
            socket.connect(new InetSocketAddress(host, port));
            return true;
        } catch (IOException var14) {
            var14.printStackTrace();
            var4 = false;
        } finally {
            try {
                socket.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        return var4;
    }

}
