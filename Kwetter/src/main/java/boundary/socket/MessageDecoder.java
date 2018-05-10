/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.socket;

import boundary.rest.dto.PostDTO;
import com.google.gson.Gson;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
class MessageDecoder implements Encoder.Text<PostDTO> {

    private static final Logger LOG = Logger.getLogger(MessageDecoder.class.getName());
    private Gson gson = new Gson();

    @Override
    public String encode(PostDTO postDTO) throws EncodeException {
        try {
            return gson.toJson(postDTO);
        } catch (Exception e) {
            throw new EncodeException(postDTO, "json decoding error", e);
        }
    }

    @Override
    public void destroy() {
        LOG.fine("encoder destroyed");
    }

    @Override
    public void init(EndpointConfig cfg) {
        LOG.fine("init encoder");
    }
}
