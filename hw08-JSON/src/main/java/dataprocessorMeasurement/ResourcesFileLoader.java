package dataprocessorMeasurement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Measurement;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {

        try (var jsonReader = new JsonReader(new InputStreamReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName)))) {
            var gson = new Gson();
            var userListType = new TypeToken<ArrayList<Measurement>>() {
            }.getType();
            return gson.fromJson(jsonReader, userListType);
        }catch (Exception e){
            throw new FileProcessException(e);
        }
    }
}
