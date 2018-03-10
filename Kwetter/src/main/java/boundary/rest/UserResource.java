package boundary.rest;

import boundary.rest.response.CreateResponse;
import boundary.rest.response.DeleteResponse;
import boundary.rest.response.GetMultipleResponse;
import boundary.rest.response.GetSingleResponse;
import boundary.rest.response.UpdateResponse;
import domain.User;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
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

    @Context
    UriInfo uriInfo;

    @Inject
    UserService userService;

    @GET
    @Path("getAllUsers")
    public GetMultipleResponse<User> getAllUsers() {
        GetMultipleResponse<User> response = new GetMultipleResponse<>();
        response.setRecords(userService.getAllUsers());
        response.setSucces(true);
        return response;
    }

    @GET
    @Path("getUser/{userId}")
    public GetSingleResponse<User> getUser(@PathParam("userId") long userId) {
        GetSingleResponse<User> response = new GetSingleResponse<>();
        try {
            response.setRecord(userService.getUser(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @GET
    @Path("getFollowers/{userId}")
    public GetMultipleResponse<User> getFollowers(@PathParam("userId") long userId) {
        GetMultipleResponse<User> response = new GetMultipleResponse<>();
        try {
            response.setRecords(userService.getFollowers(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @GET
    @Path("getFollowerCount/{userId}")
    public GetSingleResponse<Long> getFollowerCount(@PathParam("userId") long userId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(userService.getFollowerCount(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @GET
    @Path("getFollowing/{userId}")
    public GetMultipleResponse<User> getFollowing(@PathParam("userId") long userId) {
        GetMultipleResponse<User> response = new GetMultipleResponse<>();
        try {
            response.setRecords(userService.getFollowing(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @GET
    @Path("getFollowingCount/{userId}")
    public GetSingleResponse<Long> getFollowingCount(@PathParam("userId") long userId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(userService.getFollowingCount(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @POST
    @Path("createUser/{user}")
    public CreateResponse<User> createUser(@PathParam("user") User user) {
        CreateResponse<User> response = new CreateResponse<>();
        userService.createUser(user);
        response.setSucces(true);
        return response;
    }

    @PUT
    @Path("updateUser/{user}")
    public UpdateResponse<User> updateUser(@PathParam("user") User user) {
        UpdateResponse<User> response = new UpdateResponse<>();
        try {
            userService.updateUser(user);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @DELETE
    @Path("deleteResponse/{userId}")
    public DeleteResponse<User> deleteResponse(@PathParam("userId") long userId) {
        DeleteResponse<User> response = new DeleteResponse<>();
        try {
            userService.deleteUser(userId);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @PUT
    @Path("follow/{userIdFollower}/{userIdFollowing}")
    public UpdateResponse<User> follow(
            @PathParam("userIdFollower") long userIdFollower,
            @PathParam("userIdFollowing") long userIdFollowing) {
        UpdateResponse<User> response = new UpdateResponse<>();
        try {
            userService.follow(userIdFollower, userIdFollowing);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker(s) bestaat(/n) niet.");
        }
        return response;
    }

    @PUT
    @Path("unfollow/{userIdFollower}/{userIdFollowing}")
    public UpdateResponse<User> unfollow(
            @PathParam("userIdFollower") long userIdFollower,
            @PathParam("userIdFollowing") long userIdFollowing) {
        UpdateResponse<User> response = new UpdateResponse<>();
        try {
            userService.unfollow(userIdFollower, userIdFollowing);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker(s) bestaat(/n) niet.");
        }
        return response;
    }
}
