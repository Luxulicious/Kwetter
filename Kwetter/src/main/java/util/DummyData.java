/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Post;
import domain.Role;
import domain.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class DummyData {

    List<User> users = new ArrayList<>();
    List<Post> posts = new ArrayList<>();
    List<Role> roles = new ArrayList<>();

    public DummyData() {
        createRoles();
        createUsers();
        followEachother();
        giveRoles();
        createPosts();
    }

    private void createPosts() {
        for (int i = 0; i < users.size(); i++) {
            posts.add(users.get(i).post(new Post(i, "PSA " + users.get(i).getUsername(), new Timestamp(2018, 2, 26, 13, 22, 19 + i, 0), users.get(i))));
        }
    }

    private void giveRoles() {
        roles.set(0, users.get(0).setRole(roles.get(0)));
        roles.set(1, users.get(1).setRole(roles.get(1)));
        for (int i = 2; i < 10; i++) {
            roles.set(2, users.get(i).setRole(roles.get(2)));
        }
    }

    private void followEachother() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                users.get(i).follow(users.get(j));
            }
        }
    }

    private void createRoles() {
        roles.add(new Role("administrator"));
        roles.add(new Role("moderator"));
        roles.add(new Role("client"));
    }

    private void createUsers() {
        users.add(new User(0, "Admin", "password"));
        users.add(new User(1, "Mod", "password"));
        users.add(new User(2, "Piet", "password"));
        users.add(new User(3, "Tom", "password"));
        users.add(new User(4, "Mark", "password"));
        users.add(new User(5, "Bram", "password"));
        users.add(new User(6, "Sanne", "password"));
        users.add(new User(7, "Daan", "password"));
        users.add(new User(8, "Donald", "password"));
        users.add(new User(9, "Maria", "password"));
        users.add(new User(10, "steve", "f148389d080cfe85952998a8a367e2f7eaf35f2d72d2599a5b0412fe4094d65c"));
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Role> getRoles() {
        return roles;
    }

}
