/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.User;
import java.sql.SQLException;
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
public class UserDaoTest {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    private EntityManager em;
    private EntityTransaction tx;
    private UserDao userDao;

    public UserDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        try {
            new DatabaseCleaner(emf.createEntityManager()).clean();
        } catch (SQLException ex) {
            //TODO Make this a log
            Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to clean test database.", ex);
        }

        em = emf.createEntityManager();
        tx = em.getTransaction();
        userDao = new UserDao(em);
    }

    @After
    public void tearDown() {
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

}
