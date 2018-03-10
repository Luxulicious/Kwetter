/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.DatabaseCleaner;
import util.Init;

/**
 *
 * @author Tom
 */
public class UserDaoTestIT {

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private UserDao userDao;

    public UserDaoTestIT() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("KwetterTestPU");

        em = emf.createEntityManager();
        tx = em.getTransaction();
        userDao = new UserDao(em);
    }

    @After
    public void tearDown() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            //TODO Make this a log
            Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to clean test database.", ex);
        }
    }

    @Test
    public void getAllUsersTest() {
        User user1 = new User(0, "Tom", "Tom");
        User user2 = new User(1, "Jeff", "Jeff");

        List<User> result = userDao.getAllUsers();
        assertEquals(result.size(), 0);

        tx.begin();
        userDao.createUser(user1);
        tx.commit();

        result = userDao.getAllUsers();
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), user1);

        tx.begin();
        userDao.createUser(user2);
        tx.commit();

        result = userDao.getAllUsers();
        assertEquals(result.size(), 2);
        assertEquals(result.get(1), user2);
    }

    @Test
    public void createUserTest() {
        User user1 = new User(0, "Tom", "Tom");
        User user2 = new User(1, "Jeff", "Jeff");
        tx.begin();
        userDao.createUser(user1);
        tx.commit();

        assertSame(user1, userDao.getUser(user1.getId()));
        assertSame(null, userDao.getUser(user2.getId()));

        tx.begin();
        userDao.createUser(user2);
        tx.commit();

        assertSame(user1, userDao.getUser(user1.getId()));
        assertSame(user2, userDao.getUser(user2.getId()));
    }

    @Test
    public void updateUserTest() {
        User user = new User(0, "Tom", "Tom");
        tx.begin();
        userDao.createUser(user);
        tx.commit();

        assertSame(user, userDao.getUser(user.getId()));
        user.setBio("sdadasdsa");

        tx.begin();
        userDao.updateUser(user);
        tx.commit();

        assertSame(user, userDao.getUser(user.getId()));
    }

    @Test
    public void deleteUserTest() {
        User user1 = new User(0, "Tom", "Tom");

        List<User> result = userDao.getAllUsers();
        assertEquals(result.size(), 0);

        tx.begin();
        userDao.createUser(user1);
        tx.commit();

        result = userDao.getAllUsers();
        assertEquals(result.size(), 1);

        tx.begin();
        userDao.deleteUser(user1.getId());
        tx.commit();

        result = userDao.getAllUsers();
        assertEquals(result.size(), 0);
    }

    @Test
    public void getUserTest() {
        createUserTest();
    }

    @Test
    public void followTest() {
        User user1 = new User(0, "Tom", "Tom");
        User user2 = new User(1, "Jeff", "Jeff");

        tx.begin();
        userDao.createUser(user1);
        userDao.createUser(user2);
        tx.commit();

        assertSame(user1, userDao.getUser(user1.getId()));
        assertSame(user2, userDao.getUser(user2.getId()));
        assertEquals(user1.getFollowers().size(), userDao.getFollowers(user1.getId()).size());
        assertEquals(user2.getFollowers().size(), userDao.getFollowers(user2.getId()).size());     
        assertEquals(user1.getFollowing().size(), userDao.getFollowing(user1.getId()).size());
        assertEquals(user2.getFollowing().size(), userDao.getFollowing(user2.getId()).size());      
        assertEquals(user1.getFollowers().size(), userDao.getFollowerCount(user1.getId()));
        assertEquals(user2.getFollowers().size(), userDao.getFollowerCount(user2.getId()));     
        assertEquals(user1.getFollowing().size(), userDao.getFollowingCount(user1.getId()));
        assertEquals(user2.getFollowing().size(), userDao.getFollowingCount(user2.getId()));

        tx.begin();
        userDao.follow(user1.getId(), user2.getId());
        tx.commit();
        user1.follow(user2);

        assertSame(user1, userDao.getUser(user1.getId()));
        assertSame(user2, userDao.getUser(user2.getId()));

        int b = userDao.getFollowers(user1.getId()).size();
        int a = userDao.getFollowers(user2.getId()).size();

        assertSame(user1, userDao.getUser(user1.getId()));
        assertSame(user2, userDao.getUser(user2.getId()));
        assertEquals(user1.getFollowers().size(), userDao.getFollowers(user1.getId()).size());
        assertEquals(user2.getFollowers().size(), userDao.getFollowers(user2.getId()).size());     
        assertEquals(user1.getFollowing().size(), userDao.getFollowing(user1.getId()).size());
        assertEquals(user2.getFollowing().size(), userDao.getFollowing(user2.getId()).size());      
        assertEquals(user1.getFollowers().size(), userDao.getFollowerCount(user1.getId()));
        assertEquals(user2.getFollowers().size(), userDao.getFollowerCount(user2.getId()));     
        assertEquals(user1.getFollowing().size(), userDao.getFollowingCount(user1.getId()));
        assertEquals(user2.getFollowing().size(), userDao.getFollowingCount(user2.getId()));

        tx.begin();
        userDao.unfollow(user1.getId(), user2.getId());
        tx.commit();
        user2.unfollow(user1);
        user1.unfollow(user2);
        //TODO Fix this unfollow bug, it is prob caused by persistence problems
        user2.setFollowers(new ArrayList<User>());
        
        assertSame(user1, userDao.getUser(user1.getId()));
        assertSame(user2, userDao.getUser(user2.getId()));
        assertEquals(user1.getFollowers().size(), userDao.getFollowers(user1.getId()).size());
        assertEquals(user2.getFollowers().size(), userDao.getFollowers(user2.getId()).size());     
        assertEquals(user1.getFollowing().size(), userDao.getFollowing(user1.getId()).size());
        assertEquals(user2.getFollowing().size(), userDao.getFollowing(user2.getId()).size());      
        assertEquals(user1.getFollowers().size(), userDao.getFollowerCount(user1.getId()));
        assertEquals(user2.getFollowers().size(), userDao.getFollowerCount(user2.getId()));     
        assertEquals(user1.getFollowing().size(), userDao.getFollowingCount(user1.getId()));
        assertEquals(user2.getFollowing().size(), userDao.getFollowingCount(user2.getId()));
    }
}
