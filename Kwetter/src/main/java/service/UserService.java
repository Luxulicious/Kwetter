/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.UserDao;
import domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

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

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUser(int userId) {
        return userDao.getUser(userId);
    }

    public List<User> getFollowers(int userId) {
        return userDao.getFollowers(userId);
    }
    
    public long getFollowerCount(int userId)
    {
        return userDao.getFollowerCount(userId);
    }

    public List<User> getFollowing(int userId) {
        return userDao.getFollowing(userId);
    }
    
    public long getFollowingCount(int userId)
    {
        return userDao.getFollowingCount(userId);
    }
}
