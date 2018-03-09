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
}
