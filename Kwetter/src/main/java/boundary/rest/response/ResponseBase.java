package boundary.rest.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseBase {

    private boolean succes;
    private List<String> messages;

    public ResponseBase() {
        this.succes = false;
        this.messages = new ArrayList<String>();
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    
}
