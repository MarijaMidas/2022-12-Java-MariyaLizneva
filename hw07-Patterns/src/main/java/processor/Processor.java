package processor;

import model.Message;

public interface Processor {

    Message process(Message message);
}
