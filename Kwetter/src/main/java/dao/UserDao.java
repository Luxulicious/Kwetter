/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.User;
import java.util.List;
import javax.ejb.Stateless;
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
public class UserDao {

    @PersistenceContext
    EntityManager em;

    public UserDao() {
    }

    public UserDao(EntityManager em) {
        this.em = em;
    }

    public List<User> getAllUsers() {
        return em.createNamedQuery("User.getAllUsers").getResultList();
    }

    public void createUser(User user) {
        em.persist(user);
    }

    public void updateUser(User user) {
        em.merge(user);
    }

    public void deleteUser(long userId) {
        User user = getUser(userId);
        em.remove(user);
    }

    public User getUser(long userId) {
        return em.find(User.class, userId);
    }

    public void follow(long userIdFollower, long userIdFollowing) {
        User follower = getUser(userIdFollower);
        User following = getUser(userIdFollowing);
        follower.follow(following);
        em.merge(follower);
        em.merge(following);
    }

    public void unfollow(long userIdFollower, long userIdFollowing) {
        User follower = getUser(userIdFollower);
        User following = getUser(userIdFollowing);
        follower.unfollow(following);
        em.merge(follower);
        em.merge(following);
    }

    public List<User> getFollowers(long userId) {
        Query query = em.createNamedQuery("User.getFollowers");
        query.setParameter("following", getUser(userId));
        return query.getResultList();
    }

    public List<User> getFollowing(long userId) {
        Query query = em.createNamedQuery("User.getFollowing");
        query.setParameter("follower", getUser(userId));
        return query.getResultList();
    }

    //TODO Fix this named query
    public long getFollowingCount(long userId) {
        return getFollowing(userId).size();
//<editor-fold defaultstate="collapsed" desc="comment">
//        Query query = em.createNamedQuery("User.getFollowingCount");
//        query.setParameter("follower",  getUser(userId));
//        return query.getFirstResult();
//</editor-fold>
    }

    //TODO Fix this named query
    public long getFollowerCount(long userId) {
        return getFollowers(userId).size();
//<editor-fold defaultstate="collapsed" desc="comment">
//        Query query = em.createNamedQuery("User.getFollowerCount");
//        query.setParameter("following",  getUser(userId));
//        return query.getFirstResult();
//</editor-fold>
    }

    public User getUserByUsername(String username) {
        Query query = em.createNamedQuery("User.getUserByUsername");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }
}
