package listener.homework;

import model.Message;
import model.ObjectForMessage;

import java.util.ArrayList;

public class DeepCopyMessage {

    Message archive;

    public DeepCopyMessage(Message message){
        var copyMessage = new ObjectForMessage();
        var value = message.getField13().getData();
        copyMessage.setData(new ArrayList<>(value));
        archive = new  Message.Builder(message.getId())
                .field1(message.getField1())
                .field2(message.getField2())
                .field3(message.getField3())
                .field4(message.getField4())
                .field5(message.getField5())
                .field6(message.getField6())
                .field7(message.getField7())
                .field8(message.getField8())
                .field9(message.getField9())
                .field10(message.getField10())
                .field11(message.getField11())
                .field12(message.getField12())
                .field13(copyMessage)
        .build();
    }
}
