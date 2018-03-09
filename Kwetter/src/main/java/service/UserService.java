/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.exceptions.NonExistingUserException;
import dao.UserDao;
import domain.Role;
import domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.exceptions.ServiceExceptionHandler;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Stateless
public class UserService {

    @Inject
    UserDao userDao;

    @Inject
    ServiceExceptionHandler exh;

    public List<User> getAllUsers() {
        return userDao.getAllUsers(); 
    }

    public User getUser(long userId) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        return userDao.getUser(userId);
    }

    public List<User> getFollowers(long userId) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        return userDao.getFollowers(userId);
    }

    public long getFollowerCount(long userId) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        return userDao.getFollowerCount(userId);
    }

    public List<User> getFollowing(long userId) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        return userDao.getFollowing(userId);
    }

    public long getFollowingCount(long userId) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        return userDao.getFollowingCount(userId);
    }

    //TODO Add unique check
    public void createUser(User user) {
        user.setFollowers(null);
        user.setFollowing(null);
        user.setRole(new Role("cient"));
    }

    public void updateUser(User user) throws NonExistingUserException {
        exh.NonExistingUserCheck(user.getId());
        userDao.updateUser(user);
    }

    public void deleteUser(long userId) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        userDao.deleteUser(userId);
    }

    public void follow(long userIdFollower, long userIdFollowing) throws NonExistingUserException {
        exh.NonExistingUserCheck(userIdFollower);
        exh.NonExistingUserCheck(userIdFollowing);   
        userDao.follow(userIdFollower, userIdFollowing);
    }
    
    public void unfollow(long userIdFollower, long userIdFollowing) throws NonExistingUserException {
        exh.NonExistingUserCheck(userIdFollower);
        exh.NonExistingUserCheck(userIdFollowing);   
        userDao.unfollow(userIdFollower, userIdFollowing);
    }
}
