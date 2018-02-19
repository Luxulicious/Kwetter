/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Path;
import service.RoleService;

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
}
