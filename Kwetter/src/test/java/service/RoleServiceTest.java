/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.RoleDao;
import dao.UserDao;
import domain.Role;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import service.exceptions.ServiceExceptionHandler;

/**
 *
 * @author Tom
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @Mock
    private RoleDao roleDao;
    @Mock
    private ServiceExceptionHandler exh;
    @InjectMocks
    private RoleService roleService;

    private List<User> users;
    private List<Role> roles;

    public RoleServiceTest() {
    }

    @Before
    public void setUp() {
        roleService = new RoleService();
        roleService.setUserDao(roleDao);
        roleService.setExceptionHandler(exh);
        users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User(i, "User " + i, "password"));
        }
        roles = new ArrayList<>();
        roles.add(new Role("administrator"));
        roles.add(new Role("moderator"));
        roles.add(new Role("client"));

    }

    /**
     * Test of getAllRoles method, of class RoleService.
     */
    @Test
    public void getAllRolesTest() throws Exception {
        when(roleDao.getAllRoles()).thenReturn(roles);
        boolean result = roleService.getAllRoles().isEmpty();
        assertFalse(result);
    }

    /**
     * Test of setUserRole method, of class RoleService.
     */
    @Test
    public void setUserRoleTest() throws Exception {
        doNothing().when(roleDao).setUserRole(roles.get(1).getRoleName(), users.get(1).getId());
        roleService.setUserRole(roles.get(1).getRoleName(), users.get(1).getId());
    }

}
