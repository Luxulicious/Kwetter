package boundary.rest;

import boundary.rest.response.CreateResponse;
import boundary.rest.response.DeleteResponse;
import boundary.rest.response.GetMultipleResponse;
import boundary.rest.response.GetSingleResponse;
import boundary.rest.response.UpdateResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.User;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import service.UserService;
import service.exceptions.NonExistingUserException;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllUsers")
    public String getAllUsers() {
        GetMultipleResponse<User> response = new GetMultipleResponse<>();
        response.setRecords(userService.getAllUsers());
        response.setSucces(true);
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getUser/{userId}")
    public String getUser(@PathParam("userId") long userId) {
        GetSingleResponse<User> response = new GetSingleResponse<>();
        try {
            response.setRecord(userService.getUser(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getFollowers/{userId}")
    public String getFollowers(@PathParam("userId") long userId) {
        GetMultipleResponse<User> response = new GetMultipleResponse<>();
        try {
            response.setRecords(userService.getFollowers(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getFollowerCount/{userId}")
    public String getFollowerCount(@PathParam("userId") long userId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(userService.getFollowerCount(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getFollowing/{userId}")
    public String getFollowing(@PathParam("userId") long userId) {
        GetMultipleResponse<User> response = new GetMultipleResponse<>();
        try {
            response.setRecords(userService.getFollowing(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getFollowingCount/{userId}")
    public String getFollowingCount(@PathParam("userId") long userId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(userService.getFollowingCount(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createUser")
    public String createUser(User user) {
        CreateResponse<User> response = new CreateResponse<>();
        userService.createUser(user);
        response.setSucces(true);
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("updateUser")
    public String updateUser(User user) {
        UpdateResponse<User> response = new UpdateResponse<>();
        try {
            userService.updateUser(user);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deleteResponse/{userId}")
    public String deleteResponse(@PathParam("userId") long userId) {
        DeleteResponse<User> response = new DeleteResponse<>();
        try {
            userService.deleteUser(userId);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("follow/{userIdFollower}/{userIdFollowing}")
    public String follow(
            @PathParam("userIdFollower") long userIdFollower,
            @PathParam("userIdFollowing") long userIdFollowing) {
        UpdateResponse<User> response = new UpdateResponse<>();
        try {
            userService.follow(userIdFollower, userIdFollowing);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker(s) bestaat(/n) niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("unfollow/{userIdFollower}/{userIdFollowing}")
    public String unfollow(
            @PathParam("userIdFollower") long userIdFollower,
            @PathParam("userIdFollowing") long userIdFollowing) {
        UpdateResponse<User> response = new UpdateResponse<>();
        try {
            userService.unfollow(userIdFollower, userIdFollowing);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker(s) bestaat(/n) niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }
}
