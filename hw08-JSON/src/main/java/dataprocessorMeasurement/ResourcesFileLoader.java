package dataprocessorMeasurement;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import model.Measurement;
import org.checkerframework.common.returnsreceiver.qual.This;

import jakarta.json.Json;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {

        try (var json = This.class.getClassLoader().getResourceAsStream(fileName)) {
            JsonReader jsonReader = (JsonReader) Json.createReader(json);
            var list = new ArrayList<Measurement>();
            System.out.println(jsonReader);
            var gson = new Gson();
            //String json = gson.toJson(jsonReader);
            var userListType = new TypeToken<ArrayList<Measurement>>() {}.getType();
            System.out.println(userListType);
            list = gson.fromJson(jsonReader, userListType);
            for(Measurement m:list){
                System.out.println(m.getName()+"__________"+m.getValue());
            }
            return list;
        }catch (Exception e){
            throw new FileProcessException(e);
        }
    }
}
