package listener;

import model.Message;

public interface Listener {

    void onUpdated(Message msg);

}
