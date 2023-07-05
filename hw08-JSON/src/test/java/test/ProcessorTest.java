package test;

import model.Measurement;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import dataprocessorMeasurement.FileSerializer;
import dataprocessorMeasurement.ProcessorAggregator;
import dataprocessorMeasurement.ResourcesFileLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class ProcessorTest {

    @Test
    @DisplayName("Из файла читается json, обрабатывается, результат сериализуется в строку")
    void processingTest(@TempDir Path tempDir) throws IOException {
        System.out.println(tempDir);

        //given
        var inputDataFileName = "inputData.json";
        var outputDataFileName = "outputData.json";
        var fullOutputFilePath = String.format("%s%s%s",tempDir, File.separator, outputDataFileName);

        var loader = new ResourcesFileLoader(inputDataFileName);
        var processor = new ProcessorAggregator();
        var serializer = new FileSerializer(fullOutputFilePath);

        //when
        //var loadedMeasurements = loader.load();
        var meas1 = new Measurement("val1",0.0);
        var meas2 = new Measurement("val1",1.0);
        var meas3 = new Measurement("val1",2.0);
        var meas4 = new Measurement("val2",0.0);
        var meas5 = new Measurement("val2",10.0);
        var meas6 = new Measurement("val2",20.0);
        var meas7 = new Measurement("val3",10.0);
        var meas8 = new Measurement("val3",11.0);
        var meas9 = new Measurement("val3",12.0);
        var fake = new ArrayList<Measurement>();
        fake.add(meas1);
        fake.add(meas2);
        fake.add(meas3);
        fake.add(meas4);
        fake.add(meas5);
        fake.add(meas6);
        fake.add(meas7);
        fake.add(meas8);
        fake.add(meas9);
        var aggregatedMeasurements = processor.process(fake);
        serializer.serialize(aggregatedMeasurements);

        AssertionsForClassTypes.assertThat(/*loadedMeasurements*/fake.size()).isEqualTo(9);
        AssertionsForClassTypes.assertThat(aggregatedMeasurements.entrySet().size()).isEqualTo(3);

        var serializedOutput = Files.readString(Paths.get(fullOutputFilePath));
        assertThat(serializedOutput).isEqualTo("{\"val1\":3.0,\"val2\":30.0,\"val3\":33.0}");
    }
}