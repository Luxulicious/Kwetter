package boundary.rest;

import boundary.rest.response.CreateResponse;
import boundary.rest.response.GetMultipleResponse;
import boundary.rest.response.GetSingleResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Post;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import service.PostService;
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
@Path("post")
@Stateless
public class PostResource {

    @Inject
    PostService postService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllPosts")
    public String getAllPosts() {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        response.setRecords(postService.getAllPosts());
        response.setSucces(true);
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getPostsByPoster/{userId}")
    public String getPostsByPoster(@PathParam("userId") long userId) {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        try {
            response.setRecords(postService.getPostsByPoster(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getRecentPostsByPoster/{userId}/{limit}")
    public String getRecentPostsByPoster(
            @PathParam("userId") long userId,
            @PathParam("limit") int limit) {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        try {
            response.setRecords(postService.getRecentPostsByPoster(userId, limit));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getPostCountByPoster/{userId}")
    public String getPostCountByPoster(@PathParam("userId") long userId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(postService.getPostCountByPoster(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("searchPost/{input}")
    public String getPostsByQuery(@PathParam("input") String input) {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        response.setRecords(postService.searchPost(input));
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getTimeLine/{userId}/{limit}")
    public String getTimeline(@PathParam("userId") long userId, @PathParam("limit") int limit) {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        try {
            response.setRecords(postService.getTimeline(userId, limit));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createNewPost/{userId}/{content}")
    public String createNewPost(@PathParam("userId") long userId, @PathParam("content") String content) {
        CreateResponse<Post> response = new CreateResponse<>();
        try {
            postService.createNewPost(userId, content);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

}
