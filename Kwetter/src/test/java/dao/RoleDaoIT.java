/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Role;
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

/**
 *
 * @author Tom
 */
public class RoleDaoIT {

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private RoleDao roleDao;
    private UserDao userDao;

    public RoleDaoIT() {
    }

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("KwetterTestPU");

        em = emf.createEntityManager();
        tx = em.getTransaction();
        roleDao = new RoleDao(em);
        userDao = new UserDao(em);       
        roleDao.setUserDao(userDao);
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
    public void getRolesTest() {
        Role role1 = new Role("administrator");
        Role role2 = new Role("moderator");
        Role role3 = new Role("client");

        assertEquals(0, roleDao.getAllRoles().size());
        tx.begin();
        roleDao.addRole(role1.getName());
        tx.commit();
        assertEquals(1, roleDao.getAllRoles().size());
        tx.begin();
        roleDao.addRole(role2.getName());
        tx.commit();
        tx.begin();
        assertEquals(2, roleDao.getAllRoles().size());
        roleDao.addRole(role3.getName());
        tx.commit();
        assertEquals(3, roleDao.getAllRoles().size());

        assertNotNull(roleDao.getRole(role1.getName()));
        assertNotNull(roleDao.getRole(role2.getName()));
        assertNotNull(roleDao.getRole(role3.getName()));
    }

    @Test
    public void setUserRoleTest() {
        Role role1 = new Role("administrator");
        Role role2 = new Role("moderator");
        Role role3 = new Role("client");
        User user1 = new User(0, "Tom", "Tom");
        User user2 = new User(1, "Jeff", "Jeff");

        tx.begin();
        roleDao.addRole(role1.getName());
        roleDao.addRole(role2.getName());
        roleDao.addRole(role3.getName());
        tx.commit();

        tx.begin();
        userDao.createUser(user1);
        userDao.createUser(user2);
        tx.commit();

        user1.setRole(role1);
        user2.setRole(role3);

        tx.begin();
        roleDao.setUserRole(role1.getName(), user1.getId());
        roleDao.setUserRole(role3.getName(), user2.getId());
        tx.commit();

        assertEquals(user1.getRole().getName(), userDao.getUser(user1.getId()).getRole().getName());
        assertEquals(user2.getRole().getName(), userDao.getUser(user2.getId()).getRole().getName());

    }
}
