package boundary.rest;

import dto.PostDTO;
import boundary.rest.response.CreateResponse;
import boundary.rest.response.GetMultipleResponse;
import boundary.rest.response.GetSingleResponse;
import com.google.gson.Gson;
import domain.Post;
import java.util.ArrayList;
import java.util.List;
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
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        List<PostDTO> records = new ArrayList<>();
        List<Post> posts = postService.getAllPosts();
        for (int i = 0; i < posts.size(); i++) {
            records.add(new PostDTO(posts.get(i)));
        }
        response.setRecords(records);
        response.setSucces(true);
        return new Gson().toJson(response);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getPostsByPoster/{userId}")
    public String getPostsByPoster(@PathParam("userId") long userId) {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        try {
            List<PostDTO> records = new ArrayList<>();
            List<Post> posts = postService.getPostsByPoster(userId);
            for (int i = 0; i < posts.size(); i++) {
                records.add(new PostDTO(posts.get(i)));
            }
            response.setRecords(records);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return new Gson().toJson(response);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getRecentPostsByPoster/{userId}/{limit}")
    public String getRecentPostsByPoster(@PathParam("userId") long userId, @PathParam("limit") int limit) {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        try {
            List<PostDTO> records = new ArrayList<>();
            List<Post> posts = postService.getRecentPostsByPoster(userId, limit);
            for (int i = 0; i < posts.size(); i++) {
                records.add(new PostDTO(posts.get(i)));
            }
            response.setRecords(records);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return new Gson().toJson(response);
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
        return new Gson().toJson(response);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("searchPost/{input}")
    public String searchPost(@PathParam("input") String input) {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        List<PostDTO> records = new ArrayList<>();
        List<Post> posts = postService.searchPosts(input);
        for (int i = 0; i < posts.size(); i++) {
            records.add(new PostDTO(posts.get(i)));
        }
        response.setRecords(records);
        response.setSucces(true);
        return new Gson().toJson(response);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getTimeLine/{userId}/{limit}")
    public String getTimeline(@PathParam("userId") long userId, @PathParam("limit") int limit) {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        try {
            List<PostDTO> records = new ArrayList<>();
            List<Post> posts;
            posts = postService.getTimeline(userId, limit);
            for (int i = 0; i < posts.size(); i++) {
                records.add(new PostDTO(posts.get(i)));
            }
            response.setRecords(records);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return new Gson().toJson(response);

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createNewPost/{userId}/{content}")
    public String createNewPost(@PathParam("userId") long userId, @PathParam("content") String content) {
        CreateResponse<PostDTO> response = new CreateResponse<>();
        try {
            postService.createNewPost(userId, content);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return new Gson().toJson(response);
    }

}
