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

    public PostDao() {
    }

    PostDao(EntityManager em) {
        this.em = em;
    }

    public List<Post> getAllPosts() {
        return em.createNamedQuery("Post.getAllPosts").getResultList();
    }

    public List<Post> getPostsByPoster(long userId) {
        Query query = em.createNamedQuery("Post.getPostsByPoster");
        return query.setParameter("poster_id", userDao.getUser(userId)).getResultList();
    }

    public long getPostCountByPoster(long userId) {
        Query query = em.createNamedQuery("Post.getPostCountByPoster");
        return query.setParameter("poster_id", userId).getFirstResult();
    }

    public List<Post> getRecentPostsByPoster(long userId, int limit) {
        Query query = em.createNamedQuery("Post.getRecentPostsByPoster");
        query.setParameter("poster_id", userDao.getUser(userId));
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<Post> searchPost(String input) {
        Query query = em.createNamedQuery("Post.searchPost");
        query.setParameter("input", "%" + input + "%");
        return query.getResultList();
    }

    public List<Post> getTimeline(long userId, int limit) {
        Query query = em.createNamedQuery("Post.getTimeline");
        query.setParameter("user_id", userDao.getUser(userId));
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public void createNewPost(long userId, String content) {
        Post post = new Post(content, new Date(), userDao.getUser(userId));
        em.persist(post);
    }

    public void createPost(long userId, String content, Date date) {
        Post post = new Post(content, date, userDao.getUser(userId));
        em.persist(post);
    }

    void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
