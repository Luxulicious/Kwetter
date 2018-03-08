/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.exceptions;

import dao.UserDao;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Stateless
public class ExceptionHandler {

    @Inject
    UserDao userDao;

    public void NonExistingUserCheck(long userId) throws NonExistingUserException {
        if (userDao.getUser(userId) == null) {
            throw new NonExistingUserException();
        }
    }
}
