package boundary.rest;

import boundary.rest.response.*;
import com.google.gson.Gson;
import domain.User;
import boundary.rest.dto.FollowRequestDTO;
import boundary.rest.dto.LinkDTO;
import boundary.rest.dto.LogInDTO;
import boundary.rest.dto.RegistrationDTO;
import boundary.rest.dto.TokenDTO;
import boundary.rest.dto.UserDTO;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.*;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import service.UserService;
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
@Path("users")
@Stateless
public class UserResource {

    @Inject
    UserService userService;
    private final Gson gson = new Gson();

    //TODO Move this to another package/file(?)
    /**
     * Enriches a list of users with uri resources
     *
     * @param records userDTOs to enrich
     * @param uriInfo uriInfo context required for determing the path
     * @throws UriBuilderException
     * @throws IllegalArgumentException
     */
    private List<UserDTO> enrichUserDTOs(List<UserDTO> records, UriInfo uriInfo) throws UriBuilderException, IllegalArgumentException {
        List<UserDTO> newRecords = new ArrayList();
        for (int i = 0; i < records.size(); i++) {
            newRecords.add(enrichUserDTO(records.get(i), uriInfo));
        }
        return newRecords;
    }

    private UserDTO enrichUserDTO(UserDTO record, UriInfo uriInfo) {
        //Get postsUri
        String postsUri = uriInfo.getBaseUriBuilder()
                .path(PostResource.class)
                .path("getPostsByPoster")
                .path(Long.toString(record.id))
                .build()
                .toString();
        record.postsUri = new LinkDTO(postsUri, "post");
        //Get followersUri
        String followersUri = uriInfo.getBaseUriBuilder()
                .path(UserResource.class)
                .path("getFollowers")
                .path(Long.toString(record.id))
                .build()
                .toString();
        record.followersUri = new LinkDTO(followersUri, "follower");
        //Get followingUri
        String followingUri = uriInfo.getBaseUriBuilder()
                .path(UserResource.class)
                .path("getFollowing")
                .path(Long.toString(record.id))
                .build()
                .toString();
        record.followingsUri = new LinkDTO(followingUri, "following");
        return record;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(@Context UriInfo uriInfo) {
        GetMultipleResponse<UserDTO> response = new GetMultipleResponse<>();
        List<UserDTO> records = new ArrayList<>();
        List<User> users = userService.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            records.add(new UserDTO(users.get(i)));
        }
        records = enrichUserDTOs(records, uriInfo);
        response.setRecords(records);
        response.setSucces(true);
        return Response.ok(gson.toJson(response)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userId}")
    public Response getUser(@Context UriInfo uriInfo, @PathParam("userId") long userId) {
        GetSingleResponse<UserDTO> response = new GetSingleResponse<>();
        try {
            UserDTO record = new UserDTO(userService.getUser(userId));
            record = enrichUserDTO(record, uriInfo);
            response.setRecord(record);
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
    public Response getFollowers(@Context UriInfo uriInfo, @PathParam("userId") long userId) {
        GetMultipleResponse<UserDTO> response = new GetMultipleResponse<>();
        try {
            List<UserDTO> records = new ArrayList<>();
            List<User> users = userService.getFollowers(userId);
            for (int i = 0; i < users.size(); i++) {
                records.add(new UserDTO(users.get(i)));
            }
            records = enrichUserDTOs(records, uriInfo);
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
    public Response getFollowing(@Context UriInfo uriInfo, @PathParam("userId") long userId) {
        GetMultipleResponse<UserDTO> response = new GetMultipleResponse<>();
        try {
            List<UserDTO> records = new ArrayList<>();
            List<User> users = userService.getFollowing(userId);
            for (int i = 0; i < users.size(); i++) {
                records.add(new UserDTO(users.get(i)));
            }
            records = enrichUserDTOs(records, uriInfo);
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
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("register")
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
    @Path("update")
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
    @Path("delete/{userId}")
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("follow")
    public Response follow(
            FollowRequestDTO followRequestDTO) {
        UpdateResponse<UserDTO> response = new UpdateResponse<>();
        try {
            userService.follow(followRequestDTO.userIdFollower, followRequestDTO.userIdFollowing);
            response.setSucces(true);
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker(s) bestaat(/n) niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("unfollow")
    public Response unfollow(
            FollowRequestDTO followRequestDTO) {
        UpdateResponse<User> response = new UpdateResponse<>();
        try {
            userService.unfollow(followRequestDTO.userIdFollower, followRequestDTO.userIdFollowing);
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
    public Response signIn(LogInDTO logInDTO) {
        GetSingleResponse<TokenDTO> response = new GetSingleResponse<>();
        try {
            String token = userService.signIn(logInDTO.username, logInDTO.password);
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.token = token;
            tokenDTO.userId = userService.getUserByUsername(logInDTO.username).getId();
            response.setRecord(tokenDTO);
            response.setSucces(true);
        } catch (NonExistingUserException e) {
            response.addMessage("De ingevulde gegevens zijn niet geldig.");
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(gson.toJson(response)).build();
        } catch (UnsupportedEncodingException ex) {
            response.addMessage("Er ging iets mis tijdens de encoding van de authenticatie.");
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(response)).build();
        }
        return Response.ok(response)
                .entity(gson.toJson(response)).build();
    }
}
