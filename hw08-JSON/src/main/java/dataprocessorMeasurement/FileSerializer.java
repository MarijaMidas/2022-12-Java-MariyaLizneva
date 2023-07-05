package dataprocessorMeasurement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws FileProcessException, IOException {
        //формирует результирующий json и сохраняет его в файл
        Gson gson = new Gson();
        String json = gson.toJson(data);
        var file = new File(fileName);
        mapper.writeValue(file, json);
    }
}
