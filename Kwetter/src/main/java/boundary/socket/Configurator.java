/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.socket;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class Configurator extends ServerEndpointConfig.Configurator {

    private static final Logger LOG = Logger.getLogger(Configurator.class.getName());

    @Override
    public boolean checkOrigin(String originHeaderValue) {
        LOG.log(Level.FINE, "checking origin for {0}", originHeaderValue);
        return true;
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        LOG.log(Level.FINE, "modifying handshake for {0}, {1}, {2}", new Object[]{sec, request, response});
    }
}
