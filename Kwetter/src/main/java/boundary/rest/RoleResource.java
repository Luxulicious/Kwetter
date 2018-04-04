/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import dto.RoleDTO;
import boundary.rest.response.GetMultipleResponse;
import boundary.rest.response.UpdateResponse;
import com.google.gson.Gson;
import domain.Role;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.RoleService;
import service.exceptions.NonExistingRoleException;
import service.exceptions.NonExistingUserException;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Path("role")
@Stateless
public class RoleResource {

    @Inject
    RoleService roleService;
    private final Gson gson = new Gson();

    @GET
    @Path("getAllRoles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRoles() {
        GetMultipleResponse<RoleDTO> response = new GetMultipleResponse<>();
        List<RoleDTO> records = new ArrayList<>();
        List<Role> roles = roleService.getAllRoles();
        for (int i = 0; i < roles.size(); i++) {
            records.add(new RoleDTO(roles.get(i)));
        }
        response.setRecords(records);
        response.setSucces(true);
        return Response.ok(gson.toJson(response)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setUserRole/{roleName}/{userId}")
    public Response setUserRole(@PathParam("roleName") String roleName, @PathParam("userId") Long userId) {
        UpdateResponse<RoleDTO> response = new UpdateResponse<>();
        try {
            roleService.setUserRole(roleName, userId);
            response.setSucces(true);
        } catch (NonExistingRoleException ex) {
            response.addMessage("De rol " + roleName + " bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
            return Response.status(Response.Status.NOT_FOUND).entity(response).build();
        }
        return Response.ok(gson.toJson(response)).build();
    }
}
