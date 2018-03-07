/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Post;
import domain.User;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Stateless
public class PostDao {

    @PersistenceContext
    EntityManager em;

    @Inject
    UserDao userDao;

    public List<Post> getAllPosts() {
        return em.createNamedQuery("Post.getAllPosts").getResultList();
    }

    public List<Post> getPostsByPoster(long userId) {
        Query query = em.createNamedQuery("Post.getPostsByPoster");
        return query.setParameter("poster_id", userId).getResultList();
    }

    public long getPostCountByPoster(long userId) {
        Query query = em.createNamedQuery("Post.getPostCountByPoster");
        return query.setParameter("poster_id", userId).getFirstResult();
    }

    public List<Post> getRecentPostsByPoster(long userId, long limit) {
        Query query = em.createNamedQuery("Post.getRecentPostsByPoster");
        query.setParameter("limit", limit);
        query.setParameter("poster_id", userId);
        return query.getResultList();
    }

    public List<Post> getPostByQuery(String query) {
        return em.createNamedQuery("Post.getPostByQuery").getResultList();
    }

    public List<Post> getTimeline(long userId) {
        Query query = em.createNamedQuery("Post.getTimeline");
        query.setParameter("follower", userId);
        return query.getResultList();
    }

    public void createPost(long userId, String content) {
        User user = userDao.getUser(userId);
        Post post = user.post(new Post(userId, content, new Date()));
        em.merge(post);
    }
}
