/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PostDao;
import domain.Post;
import java.util.List;
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
public class PostService {

    @Inject
    PostDao postDao;

    @Inject
    ServiceExceptionHandler exh;

    public List<Post> getAllPosts() {
        return postDao.getAllPosts();
    }

    public List<Post> getPostsByPoster(long userId) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        return postDao.getPostsByPoster(userId);
    }

    //TODO Add max-min limit
    public List<Post> getRecentPostsByPoster(long userId, int limit) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        return postDao.getRecentPostsByPoster(userId, limit);
    }

    public long getPostCountByPoster(long userId) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        return postDao.getPostCountByPoster(userId);
    }

    public List<Post> searchPost(String input) {
        return postDao.searchPost(input);
    }

    public List<Post> getTimeline(long userId) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        return postDao.getTimeline(userId);
    }

    public void createPost(long userId, String content) throws NonExistingUserException {
        exh.NonExistingUserCheck(userId);
        postDao.createPost(userId, content);
    }

    void setExceptionHandler(ServiceExceptionHandler exh) {
        this.exh = exh;
    }

    void setPostDao(PostDao postDao) {
        this.postDao = postDao;
    }

}
