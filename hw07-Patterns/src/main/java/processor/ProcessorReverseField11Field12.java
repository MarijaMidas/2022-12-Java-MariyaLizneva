package processor;

import model.Message;

public class ProcessorReverseField11Field12 implements Processor {
    @Override
    public Message process(Message message) {
        var reverseFields = message.getField11();

        return message.toBuilder().field11(message.getField12()).field12(reverseFields).build();
    }
}
