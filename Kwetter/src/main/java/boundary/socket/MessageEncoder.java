/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.socket;

import boundary.rest.dto.PostDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.logging.Logger;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class MessageEncoder implements Decoder.Text<PostDTO> {

    private static final Logger LOG = Logger.getLogger(MessageDecoder.class.getName());
    private Gson gson = new Gson();

    @Override
    public PostDTO decode(String message) throws DecodeException {
        try {
            PostDTO postDTO = gson.fromJson(message, PostDTO.class);
            return postDTO;
        } catch (JsonSyntaxException e) {
            throw new DecodeException(message, "json decoding error", e);
        }
    }

    @Override
    public void destroy() {
        LOG.fine("decoder destroyed");
    }

    @Override
    public void init(EndpointConfig ec) {
        LOG.fine("init decoder");
    }

    @Override
    public boolean willDecode(String string) {
        return true;
    }

}
