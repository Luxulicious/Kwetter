/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PostDao;
import dao.UserDao;
import domain.Post;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

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

    public List<Post> getPostsByPoster(int userId) {
        return postDao.getPostsByPoster(userId);
    }

    public List<Post> getRecentPostsByPoster(int userId, int limit) {
        return postDao.getRecentPostsByPoster(userId, limit);
    }

    public long getPostCountByPoster(int userId) {
        return postDao.getPostCountByPoster(userId);
    }

    public List<Post> getPostByQuery(String query) {
        return postDao.getPostByQuery(query);
    }

    public List<Post> getTimeline(int userId) {
        return postDao.getTimeline(userId);
    }

}
