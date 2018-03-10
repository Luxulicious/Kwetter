package boundary.rest;

import boundary.rest.response.GetMultipleResponse;
import boundary.rest.response.GetSingleResponse;
import domain.Post;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import service.PostService;
import service.UserService;
import service.exceptions.NonExistingUserException;
import service.ServiceExceptionHandler;

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

    @Path("getAllPosts")
    public GetMultipleResponse<Post> getAllPosts() {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        response.setRecords(postService.getAllPosts());
        response.setSucces(true);
        return response;
    }

    @Path("getPostsByPoster/{userId}")
    public GetMultipleResponse<Post> getPostsByPoster(@PathParam("userId") long userId) {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        try {
            response.setRecords(postService.getPostsByPoster(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @Path("getRecentPostsByPoster/{userId}/{limit}")
    public GetMultipleResponse<Post> getRecentPostsByPoster(
            @PathParam("userId") long userId,
            @PathParam("limit") int limit) {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        try {
            response.setRecords(postService.getRecentPostsByPoster(userId, limit));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @Path("getPostCountByPoster/{userId}")
    public GetSingleResponse<Long> getPostCountByPoster(@PathParam("userId") long userId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(postService.getPostCountByPoster(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

    @Path("getPostsByQuery/{query}")
    public GetMultipleResponse<Post> getPostsByQuery(@PathParam("query") String query) {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        response.setRecords(postService.getPostsByQuery(query));
        return response;
    }

    @Path("getTimeLine/{userId}")
    public GetMultipleResponse<Post> getTimeline(@PathParam("userId") long userId) {
        GetMultipleResponse<Post> response = new GetMultipleResponse<>();
        try {
            response.setRecords(postService.getTimeline(userId));
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        return response;
    }

}
