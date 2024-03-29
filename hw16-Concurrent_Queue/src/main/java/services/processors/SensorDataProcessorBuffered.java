package services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import api.SensorDataProcessor;
import api.model.SensorData;
import lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import static java.util.Comparator.comparing;


public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final PriorityBlockingQueue<SensorData> dataBuffer;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.dataBuffer = new PriorityBlockingQueue<>(bufferSize, comparing(SensorData::getMeasurementTime));
    }

    @Override
    public void process(SensorData data) {
        dataBuffer.put(data);
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }

    }

    public void flush() {
        var bufferedData = new ArrayList<SensorData>();
        try {
            dataBuffer.drainTo(bufferedData, bufferSize);
            if (!bufferedData.isEmpty()) {
                writer.writeBufferedData(bufferedData);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
