package boundary.rest;

import boundary.rest.dto.PostDTO;
import boundary.rest.dto.NewPostDTO;
import boundary.rest.response.*;
import com.google.gson.Gson;
import domain.Post;
import java.util.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
@Path("posts")
@Stateless
public class PostResource {

    @Inject
    PostService postService;
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public Response getAllPosts(@Context UriInfo uriInfo, @QueryParam("query") String query, @QueryParam("posterId") Long posterId, @QueryParam("orderBy") List<String> orderBy) {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        List<PostDTO> records = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        //TODO Refactor this to service
        //<editor-fold defaultstate="collapsed" desc="Queryparams">
        //Parameterless
        if (uriInfo.getQueryParameters() != null || uriInfo.getQueryParameters().size() <= 0) {
            posts = postService.getAllPosts();
        }

        //Search query
        if (query != null || !query.equals("")) {
            posts = postService.searchPosts(query);
        }

        //Poster
        if (posterId != null) {
            try {
                if (posts == null && posts.size() <= 0) {
                    //Get posts by poster
                    posts = postService.getPostsByPoster(posterId);
                } else if (posts.size() > 0) {
                    //Compare and filter posts from query and poster
                    Set<Post> combinedPosts = new HashSet<>();
                    List<Post> posterPosts = postService.getPostsByPoster(posterId);
                    for (int i = 0; i < posterPosts.size(); i++) {
                        for (int j = 0; j < posts.size(); j++) {
                            if (posterPosts.get(i).equals(posts.get(j))) {
                                combinedPosts.add(posterPosts.get(i));
                            }
                        }
                    }
                    posts = new ArrayList<>();
                    posts.addAll(combinedPosts);
                }
            } catch (NonExistingUserException ex) {
                response.addMessage("De meegegeven poster bestaat niet.");
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
        }

        //Sort
        if (orderBy.size() > 0) {
            for (int i = 0; i < orderBy.size(); i++) {
                if (orderBy.get(i).equals("date")) {
                    posts.sort(Comparator.comparing((Post p) -> p.getDate()));
                }
            }
        }
        //</editor-fold>

        //Populate records and return response
        for (int i = 0; i < posts.size(); i++) {
            records.add(new PostDTO(posts.get(i)));
        }
        response.setRecords(records);
        response.setSucces(true);
        return Response.ok(gson.toJson(response)).build();
    }

    @Path("getPostsByPoster/{posterId}")
    public Response getPostsByPoster(@PathParam("posterId") long posterId) {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        try {
            List<PostDTO> records = new ArrayList<>();
            List<Post> posts = postService.getPostsByPoster(posterId);
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
    @Path("getRecentPostsByPoster/{posterId}/{limit}")
    public Response getRecentPostsByPoster(@PathParam("posterId") long posterId, @PathParam("limit") int limit) {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        try {
            List<PostDTO> records = new ArrayList<>();
            List<Post> posts = postService.getRecentPostsByPoster(posterId, limit);
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
    @Path("getPostCountByPoster/{posterId}")
    public Response getPostCountByPoster(@PathParam("posterId") long posterId) {
        GetSingleResponse<Long> response = new GetSingleResponse<>();
        try {
            response.setRecord(postService.getPostCountByPoster(posterId));
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
    @Path("getTimeline/{posterId}/{limit}")
    public Response getTimeline(@PathParam("posterId") long posterId, @PathParam("limit") int limit) {
        GetMultipleResponse<PostDTO> response = new GetMultipleResponse<>();
        try {
            List<PostDTO> records = new ArrayList<>();
            List<Post> posts;
            posts = postService.getTimeline(posterId, limit);
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
    @Path("createNewPostByParams/{posterId}/{content}")
    public Response createNewPost(@PathParam("posterId") long posterId, @PathParam("content") String content) {
        CreateResponse<PostDTO> response = new CreateResponse<>();
        try {
            postService.createNewPost(posterId, content);
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
