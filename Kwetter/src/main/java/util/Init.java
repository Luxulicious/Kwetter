/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.GroupDao;
import dao.PostDao;
import dao.RoleDao;
import dao.UserDao;
import domain.Group;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

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
    @Inject
    GroupDao groupDao;
    
    private DummyData dummyData;
    
    @PostConstruct
    public void init() {
        System.out.println("Initializing...");
        dummyData = new DummyData();
        createUsersAndRoles();
        followEachother();
        createPosts();
        createGroups();
        System.out.println("Done initializing");
    }
    
    private void createUsersAndRoles() {
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
            postDao.createNewPost(users.get(i).getId(), "PSA " + users.get(i).getUsername());
        }
    }
    
    private void createGroups() {
        
        Group regulars = new Group("regulars");
        List<User> regularsUsers = new ArrayList<>();        
        regularsUsers.add(userDao.getUserByUsername("steve"));
        regulars.setUsers(regularsUsers);
        groupDao.createGroup(regulars);     
    }
}
