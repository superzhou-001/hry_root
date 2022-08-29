package hry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;

public class JsonTest2 {


    public static void main(String[] args) throws IOException {


        JSONArray jsonArray = new JSONArray();

        int size = 8000000;

        File file = new File("/Users/wsj/Downloads/testBigJson");
        if(!file.exists()){
            file.createNewFile();
        }

        long s = System.currentTimeMillis();
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("[");
        for(int i = 0 ; i < size;i++){
            JSONObject object = new JSONObject();
            object.put("name","name"+i);
            object.put("age","name"+i);
            object.put("birthday","name"+i);
            object.put("sex","name"+i);
            object.put("money","name"+i);
            bufferedWriter.write(object.toJSONString());
            if(i!=size-1) {
                bufferedWriter.write(",");
            }
        }
        bufferedWriter.write("]");
        bufferedWriter.close();

        long e = System.currentTimeMillis();
        System.out.println("耗时"+(e-s));


    }


    private static void parse() throws FileNotFoundException {
        /**
         * [{
         * "name":"a"
         * },
         * {
         * "name":"b"
         * },
         * {
         * "name":"c"
         * },
         * {
         * "name":"d"
         * }
         * ]
         */
        Reader in = new FileReader("/Users/wsj/Downloads/testJson");

        int count = 3;


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
                    System.out.println("解析属性\t"+name+":"+value);
                    person.setName(value);

                }
                reader.endObject();
                objectList.add(person);

            }
            reader.endArray();
            hello(objectList);
            objectList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void  hello(ArrayList<Person> list){
        System.out.println("开始CRUD,list.size="+list.size());
        for (Person person :list){
            System.out.println("CRUD\t"+person.getName());
        }

    }

    static class Person{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
