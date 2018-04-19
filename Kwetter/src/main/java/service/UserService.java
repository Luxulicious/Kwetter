/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dao.RoleDao;
import dao.UserDao;
import domain.Role;
import domain.User;
import dto.RegistrationDTO;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.exceptions.ExistingUserException;
import service.exceptions.NonExistingUserException;
import service.exceptions.UnknownRoleError;

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
    RoleDao roleDao;

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

    public User getUserByUsername(String username) throws NonExistingUserException {
        return userDao.getUserByUsername(username);
    }

    //TODO Add unique check
    public void registerUser(RegistrationDTO reg) throws ExistingUserException, UnknownRoleError {
        exh.CheckExisingUser(reg);
        User user = new User(reg.username, reg.password);
        String client = "client";
        Role role = roleDao.getRole(client);
        if (role == null) {
            roleDao.addRole(client);
            role = roleDao.getRole(client);
            if (role == null) {
                throw new UnknownRoleError("The role " + client + " could not be added or acquired.");
            }
        }
        user.setRole(role);
        userDao.createUser(user);
    }

    public String signIn(String username, String password) throws NonExistingUserException, UnsupportedEncodingException {
        User user = checkSignIn(username, password);
        return createToken(user);
    }

    private String createToken(User user) throws UnsupportedEncodingException {
        try {
            return JWT.create()
                    .withSubject(user.getUsername())
                    .withIssuer("Tom")
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000000000))
                    .withClaim("id", user.getId())
                    .sign(Algorithm.HMAC512("SuperSecretKeyOwow"));
        } catch (UnsupportedEncodingException ex) {
            throw new UnsupportedEncodingException();
        }

        /*
        //Old version
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000000000))
                .signWith(SignatureAlgorithm.HS256, "SuperSecretKeyOwow")
                .compact();
         */
    }

    private User checkSignIn(String username, String password) throws NonExistingUserException {
        User user = getUserByUsername(username);
        if (!user.getPassword().equals(password)) {
            throw new NonExistingUserException();
        }
        return user;
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

    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    void setExceptionHandler(ServiceExceptionHandler exh) {
        this.exh = exh;
    }
}
