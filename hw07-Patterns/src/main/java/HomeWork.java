import handler.ComplexProcessor;
import listener.ListenerPrinterConsole;
import model.Message;
import processor.LoggerProcessor;
import processor.ProcessorReverseField11Field12;
import processor.ProcessorThrowExceptionInEventSecond;

import java.util.List;

public class HomeWork {

    public static void main(String[] args) {

        var processors = List.of(new ProcessorThrowExceptionInEventSecond(),
                new LoggerProcessor(new ProcessorReverseField11Field12()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field3("field3")
                .field6("field6")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(listenerPrinter);
    }
}
