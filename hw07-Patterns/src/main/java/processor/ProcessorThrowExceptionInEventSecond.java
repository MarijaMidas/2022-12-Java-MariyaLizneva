package processor;

import model.Message;
import processor.DataTime.DateTimeProvider;

import java.time.LocalDateTime;

public class ProcessorThrowExceptionInEventSecond implements Processor{

    private DateTimeProvider dateTimeProvider;

    public ProcessorThrowExceptionInEventSecond(){
        this.dateTimeProvider = LocalDateTime::now;
    }

    @Override
    public Message process(Message message) {
        var second = dateTimeProvider.getDate().getSecond();
        if(second%2 == 0){
            throw new RuntimeException("ThrowExceptionInEventSecond");
        }
        return message;
    }

    public void setDateTimeProvider(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }
}
