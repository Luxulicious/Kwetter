/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import boundary.rest.response.GetMultipleResponse;
import boundary.rest.response.UpdateResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Role;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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

    @GET
    @Path("getAllRoles")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllRoles() {
        GetMultipleResponse<Role> response = new GetMultipleResponse<>();
        response.setRecords(roleService.getAllRoles());
        response.setSucces(true);
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("setUserRole/{roleName}/{userId}")
    public String setUserRole(@PathParam("roleName") String roleName, @PathParam("userId") Long userId) {
        UpdateResponse<Role> response = new UpdateResponse<>();
        try {
            roleService.setUserRole(roleName, userId);
            response.setSucces(true);
        } catch (NonExistingRoleException ex) {
            response.addMessage("De rol " + roleName + " bestaat niet.");
        } catch (NonExistingUserException ex) {
            response.addMessage("Deze gebruiker bestaat niet.");
        }
        GsonBuilder gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting();
        return gson.create().toJson(response);

    }
}
