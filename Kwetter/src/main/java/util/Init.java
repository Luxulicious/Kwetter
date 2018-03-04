/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.PostDao;
import dao.RoleDao;
import dao.UserDao;
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
    @Inject
    RoleDao roleDao;
    @Inject
    PostDao postDao;

    @PostConstruct
    public void init() {
        System.out.println("Initializing...");
        createUsers();
        System.out.println("Done initializing");
    }

    private void createUsers() {
        //TODO Convert this to for loop
        DummyData dummyData = new DummyData();
        for (int i = 0; i < dummyData.users.size(); i++) {
            dummyData.users.get(i).setFollowers(null);
            dummyData.users.get(i).setFollowing(null);
            dummyData.users.get(i).setPosts(null);
            dummyData.users.get(i).getRole().setUserRoles(null);
            userDao.createUser(dummyData.users.get(i));
        }
    }
}
