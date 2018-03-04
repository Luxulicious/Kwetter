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
        System.out.println(users.size() + " " + posts.size() + " " + roles.size());
    }

    private void createPosts() {
        for (int i = 0; i < 10; i++) {
            posts.add(users.get(0).post(new Post(0, "PSA " + users.get(0).getUsername(), new Timestamp(2018, 2, 26, 13, 22, 19 + i, 0), users.get(0))));
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

//<editor-fold defaultstate="collapsed" desc="Old code">
//        posts.add(users.get(0).post(new Post(0, "PSA admin", new Timestamp(2018, 2, 26, 13, 22, 19, 0), users.get(0))));
//        posts.add(users.get(1).post(new Post(1, "PSA mod", new Timestamp(2018, 2, 26, 13, 22, 44, 0), users.get(1))));
//        posts.add(users.get(2).post(new Post(2, "PSA Piet", new Timestamp(2018, 2, 27, 13, 22, 44, 0), users.get(2))));
//        posts.add(users.get(3).post(new Post(3, "PSA Tom", new Timestamp(2018, 2, 27, 27, 22, 44, 0), users.get(3))));
//        posts.add(users.get(4).post(new Post(4, "PSA Mark", new Timestamp(2018, 2, 27, 10, 22, 44, 0), users.get(4))));
//        posts.add(users.get(5).post(new Post(5, "PSA Bram", new Timestamp(2018, 2, 27, 6, 22, 44, 0), users.get(5))));
//        posts.add(users.get(6).post(new Post(6, "PSA Sanne", new Timestamp(2018, 2, 27, 5, 22, 12, 0), users.get(6))));
//        posts.add(users.get(7).post(new Post(7, "PSA Daan", new Timestamp(2018, 2, 27, 1, 22, 32, 0), users.get(7))));
//        posts.add(users.get(8).post(new Post(8, "PSA Donald", new Timestamp(2018, 2, 27, 13, 22, 42, 0), users.get(8))));
//        posts.add(users.get(9).post(new Post(9, "PSA Maria", new Timestamp(2018, 2, 27, 19, 22, 5, 0), users.get(9))));
//        posts.add(new Post(0, "PSA admin", new Timestamp(2018, 2, 26, 13, 22, 19, 0), users.get(0)));
//        posts.add(new Post(1, "PSA mod", new Timestamp(2018, 2, 26, 13, 22, 44, 0), users.get(1)));
//        posts.add(new Post(2, "PSA Piet", new Timestamp(2018, 2, 27, 13, 22, 44, 0), users.get(2)));
//        posts.add(new Post(3, "PSA Tom", new Timestamp(2018, 2, 27, 27, 22, 44, 0), users.get(3)));
//        posts.add(new Post(4, "PSA Mark", new Timestamp(2018, 2, 27, 10, 22, 44, 0), users.get(4)));
//        posts.add(new Post(5, "PSA Bram", new Timestamp(2018, 2, 27, 6, 22, 44, 0), users.get(5)));
//        posts.add(new Post(6, "PSA Sanne", new Timestamp(2018, 2, 27, 5, 22, 12, 0), users.get(6)));
//        posts.add(new Post(7, "PSA Daan", new Timestamp(2018, 2, 27, 1, 22, 32, 0), users.get(7)));
//        posts.add(new Post(8, "PSA Donald", new Timestamp(2018, 2, 27, 13, 22, 42, 0), users.get(8)));
//        posts.add(new Post(9, "PSA Maria", new Timestamp(2018, 2, 27, 19, 22, 5, 0), users.get(9)));
//</editor-fold>
