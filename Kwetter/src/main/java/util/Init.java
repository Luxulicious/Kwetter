/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.PostDao;
import dao.RoleDao;
import dao.UserDao;
import domain.User;
import java.util.List;
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

    DummyData dummyData;

    @PostConstruct
    public void init() {
        System.out.println("Initializing...");
        dummyData = new DummyData();
        createUsersAndRoles();
        followEachother();
        createPosts();
        System.out.println("Done initializing");
    }

    private void createUsersAndRoles() {
        //TODO Convert this to for loop

        for (int i = 0; i < dummyData.users.size(); i++) {
            dummyData.users.get(i).setFollowers(null);
            dummyData.users.get(i).setFollowing(null);
            dummyData.users.get(i).setPosts(null);
            dummyData.users.get(i).getRole().setUserRoles(null);
            userDao.createUser(dummyData.users.get(i));
        }
    }

    private void followEachother() {
        List<User> users = userDao.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size(); j++) {
                if (!users.get(i).equals(users.get(j))) {
                    userDao.follow(users.get(i).getId(), users.get(j).getId());
                }
            } 
        }
    }

    private void createPosts() {
        List<User> users = userDao.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            postDao.createPost(users.get(i).getId(), "PSA " + users.get(i).getUsername());
        }
    }
}
