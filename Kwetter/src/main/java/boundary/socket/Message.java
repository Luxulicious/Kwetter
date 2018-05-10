/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.socket;

import java.io.Serializable;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class Message implements Serializable {

    private String content;

    public Message() {
    }

    public Message(String text) {
        this.content = text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
