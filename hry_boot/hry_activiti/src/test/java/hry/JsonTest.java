package hry;

import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class JsonTest {


    public static void main(String[] args) throws FileNotFoundException {

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
