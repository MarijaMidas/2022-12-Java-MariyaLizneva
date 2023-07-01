package listener.homework;

import listener.Listener;
import model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    List<Message> archiveMessages = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        archiveMessages.add(new DeepCopyMessage(msg).archive);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
            for (Message message : archiveMessages) {
                if (message.getId() == id) {
                    return Optional.of(message);
                }
            }
        return Optional.empty();
    }
}
