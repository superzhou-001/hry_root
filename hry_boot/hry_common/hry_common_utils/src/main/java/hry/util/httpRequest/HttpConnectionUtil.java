package hry.util.httpRequest;


/**
 * Copyright:   北京互融时代软件有限公司
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年5月18日 下午3:28:29
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 */
@SuppressWarnings("all")
public class HttpConnectionUtil {
    static final String CHASET_UTF_8 = "UTF-8";

    /**
     * get请求
     *
     * @param strUrl
     * @param param
     * @return
     */
    public static String getSend(String strUrl, String param, String coding) {
        URL url = null;
        HttpURLConnection connection = null;

        try {
            if (!StringUtils.isEmpty(param)) {
                url = new URL(strUrl + "?" + param);
            } else {
                url = new URL(strUrl);
            }
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.connect();
            connection.setConnectTimeout(100000);
            connection.setReadTimeout(100000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), coding));
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


    /**
     * post请求
     */
    public static String postSend(String strUrl, String param) {
        URL url = null;
        HttpURLConnection connection = null;
        //1
        try {
            url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            //使用长链接方式
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setUseCaches(false);
            connection.connect();
            connection.setConnectTimeout(100000);
            connection.setReadTimeout(100000);
            //POST方法时使用
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

    /**
     * 转为16进制方法
     *
     * @param str
     * @return
     */
    public static String paraTo16(String str) {
        String hs = "";
        try {
            byte[] byStr = str.getBytes("UTF-8");
            for (int i = 0; i < byStr.length; i++) {
                String temp = "";
                temp = (Integer.toHexString(byStr[i] & 0xFF));
                if (temp.length() == 1) temp = "%0" + temp;
                else temp = "%" + temp;
                hs = hs + temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hs.toUpperCase();
    }


    /**
     * Http请求
     */
    public static String doHttpRequest(String url, JSONObject reqJson) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            //设置传入参数
            StringEntity entity = new StringEntity(reqJson.toJSONString(), CHASET_UTF_8);
            entity.setContentEncoding(CHASET_UTF_8);
            entity.setContentType("application/json");
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);

            //调用异步通知接收接口
            HttpResponse resp = client.execute(httpPost);
            if (resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity he = resp.getEntity();
                String respContent = EntityUtils.toString(he, CHASET_UTF_8);
                return respContent;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * HttpClient发送post请求
     *
     * @param strURL 请求地址
     * @param map    参数
     * @return
     */
    public static String doPostQuery(String strURL, Map<String, String> map) {
        String result = null;
        HttpClient httpClient = null;
        PostMethod postMethod =null;
        try {
            // 构造HttpClient的实例
            httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(300000);
            // 创建POST方法的实例
            postMethod = new PostMethod(strURL);
            // 使用系统提供的默认的恢复策略
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 300000);
            NameValuePair[] param = new NameValuePair[map.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                param[i] = new NameValuePair(entry.getKey(), entry.getValue().toString());
                i++;
            }
            postMethod.setRequestBody(param);

            InputStream in = null;
            BufferedReader br = null;
            InputStreamReader isr =null;
            try {
                // 执行getMethod,statusCode为返回码
                int statusCode = httpClient.executeMethod(postMethod);
                StringBuffer sb = new StringBuffer();
                 in = postMethod.getResponseBodyAsStream();
                //BufferedReader br = new BufferedReader(new InputStreamReader(in));
                 isr = new InputStreamReader(in, "UTF-8");
                 br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                result = sb.toString();

            } catch (IOException e) {
                // 发生网络异常
                e.printStackTrace();
            }finally {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (in != null) {
                    in.close();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            // 释放连接
            postMethod.releaseConnection();
            ((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();
        }
        if (result == null) {
            result = "";
        }

        return result;
    }

    /**
     * 准备中间页面所需参数
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String operateParameter(String url, Map<String, String> params, String charset) {
        String ret = "";
        if (url != null && !"".equals(url)) {
            if (charset != null && !"".equals(charset)) {
                StringBuffer sb = new StringBuffer();
                String parameterUtil = getParams(params, charset);
                sb.append("<html>");
                sb.append("<head>");
                sb.append("<script type=\"text/javascript\">");
                sb.append("function redirectUrl() {");
                sb.append("document.form0.submit();");
                sb.append("}");
                sb.append("</script>");
                sb.append("</head>");
                sb.append("<body onload=\"redirectUrl()\">");
                sb.append("<form name=\"form0\" action=\"" + url + "\" method=\"post\" accept-charset=\"" + charset +
                        "\">");
                if (parameterUtil != null) {
                    sb.append(parameterUtil);
                }
                sb.append("</form>");
                sb.append("</body>");
                sb.append("</html>");
                ret = sb.toString();
            } else {
                ret = "form表单编码方式不存在";
            }
        } else {
            ret = "第三方url不存在";
        }
        return ret;
    }


    /**
     * 获取中间页面的form表单参数
     *
     * @param params
     * @return
     */
    public static String getParams(Map<String, String> params, String charset) {
        String htmlParamss = null;
        try {
            if (params != null && params.size() > 0) {
                StringBuffer sb = new StringBuffer();
                Iterator iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    sb.append("<input type=\"hidden\" name='" + key.toString() + "\' value='" + val.toString() + "' " +
                            "/>");
                }
                htmlParamss = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return htmlParamss;
    }

    /**
     * 魔蝎的请求方法(获取数据的时候用token)
     * HttpClient发送post请求
     *
     * @param strURL 请求地址
     * @param map    参数
     * @return
     */
    public static String doGetQueryMoXie(String moXieURL, String moXieType, String moXieToken, String methodURL,
                                         Map<String, String> map) {
        String returnMessage = "";
        try {
            HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager());
            GetMethod method = new GetMethod();
            URI base = new URI(moXieURL, false);
            Header header = new Header();
            header.setName(moXieType);
            header.setValue("token " + moXieToken);
            method.setRequestHeader(header);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            method.setURI(new URI(base, methodURL, false));
            // 组参数
            NameValuePair[] params = new NameValuePair[map.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params[i] = new NameValuePair(entry.getKey(), entry.getValue().toString());
                i++;
            }
            method.setQueryString(params);
            int result = client.executeMethod(method);
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),
                    "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            returnMessage = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMessage;
    }

    /**
     * 魔蝎的请求方法(创建采集任务、轮询任务状态、输入验证码用apikey)
     * HttpClient发送post请求
     *
     * @param strURL 请求地址
     * @param map    参数
     * @return
     */
    public static String doPostMoXie(String moXieURL, String moXieType, String moXieApikey, String methodURL, String
            moxieStrBody) {
        String returnMessage = "";
        try {
            HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager());
            PostMethod method = new PostMethod();
            URI base = new URI(moXieURL, false);
            method.setRequestHeader("Content-Type", "application/json;charset=utf-8");
            method.setRequestHeader("Authorization", moXieType + " " + moXieApikey);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            method.setURI(new URI(base, methodURL, false));
            // 组参数
            method.setRequestBody(moxieStrBody);

            int result = client.executeMethod(method);
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),
                    "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            returnMessage = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMessage;
    }


    /**
     * 将map对象参数转换成String=String&方式
     *
     * @param sortedMap
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String generateParams(Map<String, String> sortedMap, String charset) {
        int flag = 0;
        StringBuffer ret = new StringBuffer();
        Iterator iter = sortedMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if (val != null) {
                if (flag == 0) {
                    ret.append(key);
                    ret.append("=");
                    if (charset != null && !charset.equals("")) {
                        try {
                            ret.append(URLEncoder.encode(val.toString(), charset));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ret.append(val.toString());
                    }
                    flag++;
                } else {
                    ret.append("&");
                    ret.append(key);
                    ret.append("=");
                    if (charset != null && !charset.equals("")) {
                        try {
                            ret.append(URLEncoder.encode(val.toString(), charset));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ret.append(val.toString());
                    }
                }
            }
        }
        return ret.toString();
    }

    /**
     * 聚信立的提交方法
     *
     * @param jsonStr
     * @param requestURL
     * @return
     */
    public static String JXLRequestPost(String jsonStr, String requestURL) {
        String json = "{}";
        HttpURLConnection connection = null;
        URL url = null;
        OutputStreamWriter out = null;
        InputStream is = null;
        try {
            url = new URL(requestURL);// 创建连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setConnectTimeout(300000);
            connection.setReadTimeout(300000);// 设置读取超时:
            connection.connect();
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(jsonStr);
            out.flush();
            out.close();
            // 读取响应
            int length = (int) connection.getContentLength();// 获取长度
            is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[2048];
                int readLen = 0;
                int destPos = 0;
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                json = new String(data, "UTF-8"); // utf-8编码
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (is != null) {
                    is.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }


    // send bytes and recv bytes
    private static byte[] post(String url, byte[] bytes, String contentType) throws IOException {
        CloseableHttpClient m_HttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new ByteArrayEntity(bytes));
        if (contentType != null)
            httpPost.setHeader("Content-type", contentType);
        CloseableHttpResponse httpResponse = m_HttpClient.execute(httpPost);
        try {
            HttpEntity entityResponse = httpResponse.getEntity();
            int contentLength = (int) entityResponse.getContentLength();
            if (contentLength <= 0)
                throw new IOException("No response");
            byte[] respBuffer = new byte[contentLength];
            if (entityResponse.getContent().read(respBuffer) != respBuffer.length)
                throw new IOException("Read response buffer error");
            return respBuffer;
        } finally {
            httpResponse.close();
        }
    }

    private static byte[] post(String url, byte[] bytes) throws IOException {
        return post(url, bytes, null);
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static String postJson(String url, String str) throws IOException {
        String resp = "";
        try {
            URL urlcon = new URL(str);
            HttpURLConnection con = (HttpURLConnection) urlcon.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(4 * 1000);
            InputStream inStream = con.getInputStream();//通过输入流获取图片数据
            byte reqBuffer[] = readInputStream(inStream);
            byte[] respBuffer = post(url, reqBuffer, "application/octet-stream; charset=UTF-8");
            resp = new String(respBuffer, Charset.forName("UTF-8"));
        } catch (Exception e) {
            resp = "";
            e.printStackTrace();
        }
        return resp;
    }

    /**
     * post请求，参数为json
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String httpPostJson(String url, String json) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(json));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        return responseContent;
    }


    /**
     * 表单请求
     *
     * @param url
     * @param params
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String httpPostWithForm(String url, Map<String, String> params) throws ClientProtocolException,
            IOException {
        List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            pairList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(8000).setConnectTimeout
                (8000).setSocketTimeout(8000).build();
        httpPost.setConfig(requestConfig);

        httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
        String respContent = null;
        org.apache.http.client.HttpClient _httpClient = HttpClients.createDefault();
        HttpResponse resp = _httpClient.execute(httpPost);
        HttpEntity he = resp.getEntity();
        respContent = EntityUtils.toString(he, "UTF-8");
        return respContent;
    }


}
