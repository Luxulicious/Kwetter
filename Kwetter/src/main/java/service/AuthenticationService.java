/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.UserDao;
import javax.ejb.Stateless;
import javax.inject.Inject;
import service.exceptions.NonExistingUserException;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Stateless
public class AuthenticationService {

    @Inject
    UserService userService;

    public void authenticate(String username, String password) throws AuthenticationException, NonExistingUserException {
        if (userService.getUserByUsername(username).getPassword().equals(password)) {
            throw new AuthenticationException("Could not authenticate user: " + username + ".");
        }
    }
}
