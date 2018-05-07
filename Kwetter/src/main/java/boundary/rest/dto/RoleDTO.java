/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest.dto;

import domain.Role;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class RoleDTO {

    public String roleName;

    public RoleDTO(Role role) {
        this.roleName = role.getRoleName();
    }

    public RoleDTO() {
    }

}
