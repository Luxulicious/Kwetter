/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import service.AuthenticationService;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.AuthenticationException;
import service.exceptions.NonExistingUserException;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Path("authentication")
@Stateless
public class AuthenticationResource {

    @Inject
    AuthenticationService authService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{username}/{password}")
    public Response authenticateUser(
            @PathParam("username") String username,
            @PathParam("password") String password) {
        try {
            authententicate(username, password);
            String token = issueToken(username);
            return Response.ok(token).build();

        } catch (AuthenticationException ex) {
            return Response.status(Response.Status.FORBIDDEN).build();
        } catch (NonExistingUserException ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private void authententicate(String username, String password) throws AuthenticationException, NonExistingUserException {
        authService.authenticate(username, password);
    }

    private String issueToken(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
