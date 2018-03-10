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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
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

    @After
    public void tearDown() {
    }

    @Test
    public void getAllUsersTest() {
        when(userDao.getAllUsers()).thenReturn(users);
        boolean result = !userService.getAllUsers().isEmpty();
        assertTrue(result);
    }

    @Test
    public void getUserTest() throws NonExistingUserException {
        when(userDao.getUser(users.get(0).getId())).thenReturn(users.get(0));
        User result = userService.getUser(users.get(0).getId());
        User expected = users.get(0);
        assertSame(result, expected);
    }

    @Test
    public void getFollowersTest() throws NonExistingUserException {
        users.get(0).follow(users.get(1));
        List<User> followers = users.get(1).getFollowers();
        when(userDao.getFollowers(users.get(1).getId())).thenReturn(followers);
        List<User> result = userService.getFollowers(users.get(1).getId());
        List<User> expected = users.get(1).getFollowers();
        assertSame(result, expected);
    }

    @Test
    public void getFollowerCountTest() throws Exception {
        users.get(0).follow(users.get(1));
        List<User> followers = users.get(1).getFollowers();
        when(userDao.getFollowerCount(users.get(1).getId())).thenReturn(new Long(followers.size()));
        Long result = userService.getFollowerCount(users.get(1).getId());
        Long expected = Long.valueOf(users.get(1).getFollowers().size());
        assertSame(result, expected);
    }

    /**
     * Test of getFollowing method, of class UserService.
     */
    @Test
    public void getFollowingTest() throws Exception {
        users.get(0).follow(users.get(1));
        List<User> following = users.get(0).getFollowing();
        when(userDao.getFollowing(users.get(0).getId())).thenReturn(following);
        List<User> result = userService.getFollowing(users.get(0).getId());
        List<User> expected = users.get(0).getFollowing();
        assertSame(result, expected);
    }

    /**
     * Test of getFollowingCount method, of class UserService.
     */
    @Test
    public void getFollowingCountTest() throws Exception {
        users.get(0).follow(users.get(1));
        List<User> following = users.get(0).getFollowing();
        when(userDao.getFollowingCount(users.get(0).getId())).thenReturn(new Long(following.size()));
        Long result = userService.getFollowingCount(users.get(0).getId());
        Long expected = Long.valueOf(users.get(0).getFollowing().size());
        assertSame(result, expected);
    }

    /**
     * Test of createUser method, of class UserService.
     */
    @Test
    public void createUserTest() throws Exception {
        User userToAdd = new User(31478941, "Tom", "asfhosahfgdsga");
        doNothing().when(userDao).createUser(userToAdd);
        userService.createUser(userToAdd);
    }

    /**
     * Test of updateUser method, of class UserService.
     */
    @Test
    public void updateUserTest() throws Exception {
        User userToUpdate = new User(0, "Jeff", "asdasrfdgd");
        doNothing().when(userDao).updateUser(users.get(0));
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
