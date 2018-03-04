/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.UserDao;
import domain.User;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Startup
@Singleton
public class Init {

    @Inject
    UserDao userDao;

    @PostConstruct
    public void init() {
        System.out.println("Initializing...");
        User user = new User(0, "Tom", "Geheim");
        userDao.createUser(user);
        System.out.println("Done initializing");
    }
}
