package boundary.socket;

import boundary.rest.dto.PostDTO;
import domain.Post;
import domain.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.DecodeException;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import service.PostService;
import service.UserService;
import service.exceptions.NonExistingUserException;
import sun.awt.PeerEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@ServerEndpoint(
        value = "/KwetterServerEndPoint/{username}",
        encoders = {MessageDecoder.class},
        decoders = {MessageEncoder.class},
        configurator = Configurator.class
)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Singleton
public class EndPoint {

    private static final Logger LOG = Logger.getLogger(EndPoint.class.getName());
    private Map<Session, String> activeSessions;
    private MessageDecoder encoder;
    private MessageEncoder decoder;

    @Inject
    private UserService userService;

    @Inject
    private PostService postService;

    public EndPoint() {
        this.activeSessions = Collections.synchronizedMap(new HashMap<>());
        this.encoder = new MessageDecoder();
        this.decoder = new MessageEncoder();
    }

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        LOG.log(Level.FINE, "opened session by {0}", username);
        this.activeSessions.put(session, username);
    }

    @OnClose
    public void onClose(Session session, EndpointConfig endPointConfig, CloseReason closeReason) {
        LOG.log(Level.FINE, "Closed session {0} \n"
                + "Reason: {1} \n"
                + "EndPointConfig: {2}",
                new Object[]{session, closeReason, endPointConfig});
        this.activeSessions.remove(session);
    }

    @OnError
    public void onError(Throwable throwable, Session session) {
        LOG.log(
                Level.SEVERE,
                new StringBuilder("an error occured for session ").append(session).toString(),
                throwable
        );
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        LOG.log(Level.INFO, "message: {0}", message);
        try {
            PostDTO postDTO = decoder.decode(message);
            Post post = postService.createNewPost(postDTO.poster.id, postDTO.content);
            String socketPost = encoder.encode(new PostDTO(post));
            User user = userService.getUser(post.getPoster().getId());
            LOG.log(Level.INFO, "newpost: {0}", socketPost);
            broadcast(socketPost, user);
        } catch (NonExistingUserException ex) {
            LOG.log(Level.SEVERE, "failed to create post socket. No such poster exists.", ex);
        } catch (DecodeException ex) {
            LOG.log(Level.SEVERE, "failed to decode post", ex);
        } catch (EncodeException ex) {
            LOG.log(Level.SEVERE, "failed to encode post", ex);
        }
    }

    private void broadcast(String message, User user) {
        List<User> followers = user.getFollowers();
        for (User follower : followers) {
            if (activeSessions.containsValue(follower.getUsername())) {
                for (Session session : activeSessions.keySet()) {
                    sendMessage(session, message);
                }
            }
        }
    }

    private void sendMessage(Session session, String message) {
        if (session.isOpen()) {
            try {
                session.getBasicRemote().sendObject(message);
            } catch (EncodeException | IOException ex) {
                LOG.log(Level.SEVERE, "failed to send message", ex);
            }

        }
    }

}
