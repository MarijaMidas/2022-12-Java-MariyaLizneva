package listener.homework;

import listener.Listener;
import model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long,Message> archiveMessages = new TreeMap<>();

    @Override
    public void onUpdated(Message msg) {
        archiveMessages.put(msg.getId(), new DeepCopyMessage(msg).archive);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        if(archiveMessages.containsKey(id)){
            return Optional.of(archiveMessages.get(id));
        }
        return Optional.empty();
    }
}
