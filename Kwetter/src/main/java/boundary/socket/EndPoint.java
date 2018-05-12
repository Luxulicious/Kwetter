package boundary.socket;

import boundary.rest.dto.*;
import domain.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import javax.ejb.*;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.*;
import service.*;
import service.exceptions.*;

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
        for (String name : activeSessions.values()) {
            if (username.equals(name)) {
                LOG.log(Level.WARNING, "Duplicate name found in session map: {0}", name);
                return;
            }

        }
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
            for (Map.Entry<Session, String> entry : activeSessions.entrySet()) {
                if (entry.getValue().equals(follower.getUsername())) {
                    sendMessage(entry.getKey(), message);
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
