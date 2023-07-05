package dataprocessorMeasurement;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        var file = new File(fileName);
        try {
            mapper.writeValue(file, data);
        }catch (Exception e){
            throw new FileProcessException("Не удалось записать файл");
        }
    }
}
