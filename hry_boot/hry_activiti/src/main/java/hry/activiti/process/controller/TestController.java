package hry.activiti.process.controller;

import com.google.gson.stream.JsonReader;
import hry.bean.JsonResult;
import hry.core.mvc.controller.BaseController;
import hry.security.jwt.annotation.UnAuthentication;
import hry.util.serialize.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Api(value = "测试bigJson", tags = "测试bigJson", description = "测试bigJson")
@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController extends BaseController {

    private final String filePath = "/Users/wsj/Downloads/testRequstJson";
    private final String url = "http://localhost:7300/flow/testBigJson";



    @ApiOperation(value = "bigJson", notes = "bigJson")
    @GetMapping("/bigJson")
    @UnAuthentication
    public JsonResult bigJson(HttpServletRequest request) throws IOException {

        long s = System.currentTimeMillis();

        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            URL restServiceURL = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");
            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                        + httpConnection.getResponseCode());
            }
            InputStream inStrm = httpConnection.getInputStream();
            byte []b=new byte[1024];
            int length=-1;
            while((length=inStrm.read(b))!=-1){
                bufferedWriter.write(new String(b,0,length));
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            bufferedWriter.close();
        }

        int parse = parse();

        long e = System.currentTimeMillis();

        System.out.println("执行时间="+(e-s));

        return new JsonResult().setObj(parse);
    }


    //解析
    private int parse() throws FileNotFoundException {
        Reader in = new FileReader(filePath);

        int count = 10000;
        int totalCount = 0;

        ArrayList<Person> objectList = new ArrayList<>();
        JsonReader reader  = new JsonReader(in);

        try {
            reader.beginArray();
            while (reader.hasNext()) {
                if (objectList.size() >= count) {
                    hello(objectList);
                    objectList.clear();
                }
                Person person = new Person();
                reader.beginObject();

                while(reader.hasNext()) {
                    String name = reader.nextName();
                    String value = reader.nextString();
                    //System.out.println("解析属性\t"+name+":"+value);
                    person.setName(value);
                }
                reader.endObject();
                totalCount++;
                objectList.add(person);

            }
            reader.endArray();
            hello(objectList);
            objectList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("totalCount="+totalCount);
        return totalCount;
    }

    private void hello(ArrayList<Person>  list){
        System.out.println("开始CRUD,list.size="+list.size());
        for (Person person :list){
           // System.out.println("CRUD\t"+person.getName());
        }

        Lock lock  = new ReentrantLock();
    }



}
