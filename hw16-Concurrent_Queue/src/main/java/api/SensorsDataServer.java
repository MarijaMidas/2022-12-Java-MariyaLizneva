package api;

import api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
