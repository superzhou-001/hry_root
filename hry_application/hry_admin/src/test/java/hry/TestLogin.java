package hry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hry.util.HttpRequestUtil;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestLogin {


    final static String url = "http://127.0.0.1:7350";

    public static void main(String[] args) {



        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for(int i = 1; i <= 100; i++){

            int id = i;
            Thread thread = new Thread(()->{

                Map<String,Object> map = new HashMap<>();
                String userName = "test"+ id;
                map.put("userName",userName);
                map.put("passWord","123456");
                String post = HttpRequestUtil.post(url + "/auth/login", map);
                System.out.println("登录/t"+map.get("userName"));
                if(!StringUtils.isEmpty(post)){
                    JSONObject jsonObject = JSON.parseObject(post);
                    JSONObject obj = jsonObject.getJSONObject("obj");
                    String token = obj.getString("token");
                    //System.out.println(token);

                    while (true){
                        try {
                            TimeUnit.MILLISECONDS.sleep(200L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        String currentUserStr = post(url + "/auth/currentUser", new HashMap<>(), token);
                        if(!StringUtils.isEmpty(currentUserStr)) {
                            JSONObject obj1 = JSON.parseObject(currentUserStr);
                            JSONObject user = obj1.getJSONObject("user");
                            if(user!=null) {
                                String httpUser = user.getString("userName");
                                System.out.println("loginUser=" + userName + "\tcurrentUserName=" + httpUser + "\t" + userName.equals(httpUser));
                                if (!userName.equals(httpUser)) {
                                    System.out.println("------------------------------------------------------------------------------------------------------------------------------" + userName);
                                }
                            }else{
                                System.out.println(currentUserStr);
                            }
                        }
                    }


                }

            });

            executorService.submit(thread);
        }

        executorService.shutdown();


    }


    public static String post(String url,Map<String, Object> param,String token) {
        StringBuffer buffer = new StringBuffer();
        try {
            StringBuilder params=new StringBuilder();
            for(Map.Entry<String, Object> entry:param.entrySet()){
                params.append(entry.getKey());
                params.append("=");
                params.append(entry.getValue().toString());
                params.append("&");
            }
            if(params.length()>0){
                params.deleteCharAt(params.lastIndexOf("&"));
            }
            URL restServiceURL = new URL(url+(params.length()>0 ? "?"+params.toString() : ""));
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Accept", "application/json;charset=utf-8");
            httpConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            httpConnection.setRequestProperty("token",token);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpConnection.setDoInput(true);
            // Post 请求不能使用缓存
            httpConnection.setUseCaches(false);
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP POST Request Failed with Error code : "
                        + httpConnection.getResponseCode());
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "utf-8"));

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

}
