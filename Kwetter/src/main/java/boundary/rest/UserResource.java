package boundary.rest;

import boundary.rest.response.CreateResponse;
import boundary.rest.response.DeleteResponse;
import boundary.rest.response.GetMultipleResponse;
import boundary.rest.response.GetSingleResponse;
import boundary.rest.response.UpdateResponse;
import com.google.gson.Gson;
import domain.User;
import dto.RegistrationDTO;
import dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.UserService;
import service.exceptions.ExistingUserException;
import service.exceptions.NonExistingUserException;
import service.exceptions.UnknownRoleError;

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
@Path("user")
@Stateless
public class UserResource {

    @Inject
    UserService userService;
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllUsers")
    public Response getAllUsers() {
        GetMultipleResponse<UserDTO> response = new GetMultipleResponse<>();
        List<UserDTO> records = new ArrayList<>();
        List<User> users = userService.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            records.add(new UserDTO(users.get(i)));
        }
        response.setRecords(records);
        response.setSucces(true);
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getUser/{userId}")
    public Response getUser(@PathParam("userId") long userId) {
        GetSingleResponse<UserDTO> response = new GetSingleResponse<>();
        try {
            response.setRecord(new UserDTO(userService.getUser(userId)));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getFollowers/{userId}")
    public Response getFollowers(@PathParam("userId") long userId) {
        GetMultipleResponse<UserDTO> response = new GetMultipleResponse<>();
        try {
            List<UserDTO> records = new ArrayList<>();
            List<User> users = userService.getFollowers(userId);
            for (int i = 0; i < users.size(); i++) {
                records.add(new UserDTO(users.get(i)));
            }
            response.setRecords(records);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getFollowerCount/{userId}")
    public Response getFollowerCount(@PathParam("userId") long userId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(userService.getFollowerCount(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getFollowing/{userId}")
    public Response getFollowing(@PathParam("userId") long userId) {
        GetMultipleResponse<User> response = new GetMultipleResponse<>();
        try {
            List<UserDTO> records = new ArrayList<>();
            List<User> users = userService.getFollowing(userId);
            for (int i = 0; i < users.size(); i++) {
                records.add(new UserDTO(users.get(i)));
            }
            response.setRecords(users);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();

        }
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getFollowingCount/{userId}")
    public Response getFollowingCount(@PathParam("userId") long userId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(userService.getFollowingCount(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("registerUser")
    public Response registerUser(RegistrationDTO reg) {
        CreateResponse<RegistrationDTO> response = new CreateResponse<>();
        try {
            userService.registerUser(reg);
            response.setSucces(true);
        } catch (ExistingUserException ex) {
            response.addMessage("Een gebruiker met deze naam bestaat al.");
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        } catch (UnknownRoleError ex) {
            response.addMessage("Er treden een onbekende fout probeer het later nogmaals.");
            Logger.getLogger(UserResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("updateUser")
    public Response updateUser(User user) {
        UpdateResponse<UserDTO> response = new UpdateResponse<>();
        try {
            userService.updateUser(user);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deleteResponse/{userId}")
    public Response deleteResponse(@PathParam("userId") long userId) {
        DeleteResponse<UserDTO> response = new DeleteResponse<>();
        try {
            userService.deleteUser(userId);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("follow/{userIdFollower}/{userIdFollowing}")
    public Response follow(
            @PathParam("userIdFollower") long userIdFollower,
            @PathParam("userIdFollowing") long userIdFollowing) {
        UpdateResponse<UserDTO> response = new UpdateResponse<>();
        try {
            userService.follow(userIdFollower, userIdFollowing);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker(s) bestaat(/n) niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("unfollow/{userIdFollower}/{userIdFollowing}")
    public Response unfollow(
            @PathParam("userIdFollower") long userIdFollower,
            @PathParam("userIdFollowing") long userIdFollowing) {
        UpdateResponse<User> response = new UpdateResponse<>();
        try {
            userService.unfollow(userIdFollower, userIdFollowing);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker(s) bestaat(/n) niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("signIn")
    public Response signIn(UserDTO userDTO) {
        GetSingleResponse<String> response = new GetSingleResponse<>();
        try {
            String token = userService.signIn(userDTO.username, userDTO.password);
            response.setRecord(token);
            response.setSucces(true);
        } catch (NonExistingUserException e) {
            //TODO Proper exception throwing
            response.addMessage("De ingevulde gegevens zijn niet geldig.");
            return Response.status(Response.Status.FORBIDDEN).entity(response).build();
        }
        return Response.ok(response).build();
    }
}
