/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import service.exceptions.NonExistingPostException;
import dao.PostDao;
import service.exceptions.ExistingUserException;
import dto.UserDTO;
import dao.RoleDao;
import dao.UserDao;
import dto.RegistrationDTO;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.exceptions.NonExistingRoleException;
import service.exceptions.NonExistingUserException;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Stateless
public class ServiceExceptionHandler {

    @Inject
    UserDao userDao;

    @Inject
    RoleDao roleDao;

    @Inject
    PostDao postDao;

    public void NonExistingUserCheck(long userId) throws NonExistingUserException {
        if (userDao.getUser(userId) == null) {
            throw new NonExistingUserException();
        }
    }

    public void NonExistingRoleCheck(String roleName) throws NonExistingRoleException {
        if (roleDao.getRole(roleName) == null) {
            throw new NonExistingRoleException();
        }
    }

    public void CheckExisingUser(RegistrationDTO reg) throws ExistingUserException {
        if (userDao.getUserByUsername(reg.username) != null) {
            throw new ExistingUserException();
        }
    }

    public void NonExistingPostCheck(long postId) throws NonExistingPostException {
        if (postDao.getPost(postId) == null) {
            throw new NonExistingPostException();
        }
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }
}
