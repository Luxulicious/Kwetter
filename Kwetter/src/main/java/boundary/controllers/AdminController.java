/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.controllers;

import domain.Post;
import domain.User;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import service.PostService;
import service.UserService;
import service.exceptions.NonExistingPostException;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Named
@SessionScoped
public class AdminController implements Serializable {

    @Inject
    private UserService userService;

    @Inject
    private PostService postService;

    private Collection<User> users;

    private Collection<Post> posts;

    private String searchCriteria;

    public AdminController() {
    }

    @PostConstruct
    public void init() {
        users = userService.getAllUsers();
        posts = postService.getAllPosts();
    }

    public void searchPosts() {
        if (!"".equals(searchCriteria) || searchCriteria != null) {
            posts = postService.searchPosts(searchCriteria);
        } else {
            posts = postService.getAllPosts();
        }
    }

    public void deletePost(long postId) {
        try {
            postService.deletePost(postId);
            for (int i = 0; i < posts.size(); i++) {
                if (((List<Post>) posts).get(i).getId() == postId) {
                    ((List<Post>) posts).remove(i);
                }
            }
        } catch (NonExistingPostException ex) {
            //Do nothing
        }
    }

    public List<User> getUsers() {
        return (List<User>) users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

}
