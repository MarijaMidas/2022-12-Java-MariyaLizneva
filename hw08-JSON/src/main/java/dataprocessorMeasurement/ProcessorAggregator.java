package dataprocessorMeasurement;

import model.Measurement;

import java.util.*;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        var map = new TreeMap<String, Double>();
        data.forEach(obj-> map.merge(obj.getName(), obj.getValue(), Double::sum));
        return  map;
    }
}
