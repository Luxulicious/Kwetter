/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.exceptions.NonExistingRoleException;
import dao.RoleDao;
import domain.Role;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.exceptions.NonExistingUserException;
import service.exceptions.ServiceExceptionHandler;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Stateless
public class RoleService {
    
    @Inject
    RoleDao roleDao;
    
    @Inject
    ServiceExceptionHandler exh;
    
    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }
    
    public void setUserRole(String roleName, long userId) throws NonExistingRoleException, NonExistingUserException {
        exh.NonExistingRoleCheck(roleName);
        exh.NonExistingUserCheck(userId);
        roleDao.setUserRole(roleName, userId);     
    }
}
