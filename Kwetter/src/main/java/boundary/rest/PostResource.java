package boundary.rest;

import boundary.rest.response.CreateResponse;
import boundary.rest.response.GetMultipleResponse;
import boundary.rest.response.GetSingleResponse;
import com.google.gson.Gson;
import domain.Post;
import dto.NewPostDTO;
import dto.PostDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllPosts")
    public Response getAllPosts() {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        List<PostDTO> records = new ArrayList<>();
        List<Post> posts = postService.getAllPosts();
        for (int i = 0; i < posts.size(); i++) {
            records.add(new PostDTO(posts.get(i)));
        }
        response.setRecords(records);
        response.setSucces(true);
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getPostsByPoster/{userId}")
    public Response getPostsByPoster(@PathParam("userId") long userId) {
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
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getRecentPostsByPoster/{userId}/{limit}")
    public Response getRecentPostsByPoster(@PathParam("userId") long userId, @PathParam("limit") int limit) {
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
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getPostCountByPoster/{userId}")
    public Response getPostCountByPoster(@PathParam("userId") long userId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(postService.getPostCountByPoster(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("searchPosts/{input}")
    public Response searchPost(@PathParam("input") String input) {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        List<PostDTO> records = new ArrayList<>();
        List<Post> posts = postService.searchPosts(input);
        for (int i = 0; i < posts.size(); i++) {
            records.add(new PostDTO(posts.get(i)));
        }
        response.setRecords(records);
        response.setSucces(true);
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getTimeline/{userId}/{limit}")
    public Response getTimeline(@PathParam("userId") long userId, @PathParam("limit") int limit) {
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
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @POST
    @JWTTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createNewPostByParams/{userId}/{content}")
    public Response createNewPost(@PathParam("userId") long userId, @PathParam("content") String content) {
        CreateResponse<PostDTO> response = new CreateResponse<>();
        try {
            postService.createNewPost(userId, content);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @JWTTokenNeeded
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("createNewPost")
    public Response createNewPostWithBody(NewPostDTO newPostDTO) {
        CreateResponse<NewPostDTO> response = new CreateResponse<>();
        try {
            postService.createNewPost(newPostDTO.posterId, newPostDTO.content);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

}
