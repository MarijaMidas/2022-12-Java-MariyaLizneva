package dataprocessorMeasurement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data){
        try (var jsonWriter = new JsonWriter(new BufferedWriter(new FileWriter(fileName)))) {
            var gson = new Gson();
            var userListType = new TypeToken<Map<String,Double>>() {}.getType();
            gson.toJson(data, userListType, jsonWriter);
        }catch (Exception e){
            throw new FileProcessException("Не удалось записать файл");
        }
    }
}
