/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.UserDao;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import service.exceptions.NonExistingRoleException;
import service.exceptions.NonExistingUserException;
import service.exceptions.ServiceExceptionHandler;

/**
 *
 * @author Tom
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;
    @Mock
    private ServiceExceptionHandler exh;
    @InjectMocks
    private UserService userService;

    private List<User> users;

    public UserServiceTest() {
    }

    @Before
    public void setUp() {
        userService = new UserService();
        userService.setUserDao(userDao);
        userService.setExceptionHandler(exh);

        users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User(i, "User " + i, "password"));
        }
    }

    @Test
    public void getAllUsersTest() {
        when(userDao.getAllUsers()).thenReturn(users);
        boolean result = userService.getAllUsers().isEmpty();
        assertFalse(result);
    }

    @Test
    public void getUserTest() throws NonExistingUserException {
        User expected = users.get(0);
        when(userDao.getUser(users.get(0).getId())).thenReturn(expected);
        User result = userService.getUser(users.get(0).getId());
        assertSame(result, expected);
    }

    @Test
    public void getFollowersTest() throws NonExistingUserException {
        users.get(0).follow(users.get(1));
        List<User> expected = users.get(1).getFollowers();
        when(userDao.getFollowers(users.get(1).getId())).thenReturn(expected);
        List<User> result = userService.getFollowers(users.get(1).getId());
        assertSame(result, expected);
    }

    @Test
    public void getFollowerCountTest() throws Exception {
        users.get(0).follow(users.get(1));
        Long expected = Long.valueOf(users.get(1).getFollowers().size());
        when(userDao.getFollowerCount(users.get(1).getId())).thenReturn(expected);
        Long result = userService.getFollowerCount(users.get(1).getId());
        assertSame(result, expected);
    }

    /**
     * Test of getFollowing method, of class UserService.
     */
    @Test
    public void getFollowingTest() throws Exception {
        users.get(0).follow(users.get(1));
        List<User> expected = users.get(0).getFollowing();
        when(userDao.getFollowing(users.get(0).getId())).thenReturn(expected);
        List<User> result = userService.getFollowing(users.get(0).getId());
        assertSame(result, expected);
    }

    /**
     * Test of getFollowingCount method, of class UserService.
     */
    @Test
    public void getFollowingCountTest() throws Exception {
        users.get(0).follow(users.get(1));
        Long expected = Long.valueOf(users.get(0).getFollowing().size());
        when(userDao.getFollowingCount(users.get(0).getId())).thenReturn(expected);
        Long result = userService.getFollowingCount(users.get(0).getId());
        assertSame(result, expected);
    }

    /**
     * Test of createUser method, of class UserService.
     */
    @Test
    public void createUserTest() throws Exception {
        User userToAdd = new User(31478941, "Tom", "asfhosahfgdsga");
        userService.createUser(userToAdd);
    }

    /**
     * Test of updateUser method, of class UserService.
     */
    @Test
    public void updateUserTest() throws Exception {
        User userToUpdate = new User(0, "Jeff", "asdasrfdgd");
        userService.updateUser(userToUpdate);
    }

    /**
     * Test of deleteUser method, of class UserService.
     */
    @Test
    public void deleteUserTest() throws Exception {
        doNothing().when(userDao).deleteUser(users.get(0).getId());
        userService.deleteUser(0);
    }

    /**
     * Test of follow method, of class UserService.
     */
    @Test
    public void followTest() throws Exception {
        users.get(0).follow(users.get(1));
        doNothing().when(userDao).follow(users.get(0).getId(), users.get(1).getId());
        userService.follow(users.get(0).getId(), users.get(1).getId());
    }

    /**
     * Test of unfollow method, of class UserService.
     */
    @Test
    public void testUnfollow() throws Exception {
        users.get(0).follow(users.get(1));
        users.get(0).unfollow(users.get(1));
        doNothing().when(userDao).unfollow(users.get(0).getId(), users.get(1).getId());
        userService.unfollow(users.get(0).getId(), users.get(1).getId());
    }
}
