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
        em.detach(user);
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<User> getFollowers(long userId) {
        Query query = em.createNamedQuery("User.getFollowers");
        query.setParameter("following", userId);
        return query.getResultList();
    }

    public List<User> getFollowing(long userId) {
        Query query = em.createNamedQuery("User.getFollowing");
        query.setParameter("follower", userId);
        return query.getResultList();
    }

    public long getFollowingCount(long userId) {
        Query query = em.createNamedQuery("User.getFollowingCount");
        query.setParameter("follower", userId);
        return query.getFirstResult();
    }

    public long getFollowerCount(long userId) {
        Query query = em.createNamedQuery("User.getFollowerCount");
        query.setParameter("following", userId);
        return query.getFirstResult();
    }
}
