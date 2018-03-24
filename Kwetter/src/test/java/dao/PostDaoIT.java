/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Post;
import domain.User;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class PostDaoIT {

    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction tx;
    private PostDao postDao;
    private UserDao userDao;

    public PostDaoIT() {
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
        postDao = new PostDao(em);
        userDao = new UserDao(em);
        postDao.setUserDao(userDao);
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

    /**
     * Test of getAllPosts method, of class PostDao.
     */
    @Test
    public void getCreateGetPostsTest() throws Exception {
        User user1 = new User(0, "Tom", "Tom");
        User user2 = new User(1, "Jeff", "Jeff");

        tx.begin();
        userDao.createUser(user1);
        userDao.createUser(user2);
        tx.commit();

        assertEquals(postDao.getAllPosts().size(), 0);
        assertEquals(postDao.getPostsByPoster(user1.getId()).size(), 0);
        assertEquals(postDao.getPostsByPoster(user2.getId()).size(), 0);

        tx.begin();
        postDao.createNewPost(user1.getId(), "Content1");
        postDao.createNewPost(user1.getId(), "Content2");
        postDao.createNewPost(user2.getId(), "Content3");
        tx.commit();

        assertEquals(postDao.getAllPosts().size(), 3);
        assertEquals(postDao.getPostsByPoster(user1.getId()).size(), 2);
        assertEquals(postDao.getPostsByPoster(user2.getId()).size(), 1);
    }

    @Test
    public void getRecentPostsByPosterTest() throws Exception {
        User user1 = new User(0, "Tom", "Tom");
        User user2 = new User(1, "Jeff", "Jeff");

        tx.begin();
        userDao.createUser(user1);
        userDao.createUser(user2);
        tx.commit();

        tx.begin();
        postDao.createNewPost(user1.getId(), "Content1");
        postDao.createNewPost(user1.getId(), "Content2");
        postDao.createNewPost(user2.getId(), "Content3");
        postDao.createNewPost(user1.getId(), "Content4");
        postDao.createNewPost(user1.getId(), "Content5");
        postDao.createNewPost(user2.getId(), "Content6");
        tx.commit();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date date = cal.getTime();
        tx.begin();
        postDao.createPost(user1.getId(), "Content7", date);
        postDao.createPost(user1.getId(), "Content8", date);
        postDao.createPost(user1.getId(), "Content9", date);
        tx.commit();

        List<Post> result = postDao.getRecentPostsByPoster(user1.getId(), 3);
        assertEquals(result.size(), 3);
        assertTrue(!result.contains(postDao.getPostsByPoster(user1.getId()).get(3)));
        assertTrue(!result.contains(postDao.getPostsByPoster(user1.getId()).get(4)));
        assertTrue(!result.contains(postDao.getPostsByPoster(user1.getId()).get(5)));
        assertTrue(result.contains(postDao.getPostsByPoster(user1.getId()).get(0)));
        assertTrue(result.contains(postDao.getPostsByPoster(user1.getId()).get(1)));
        assertTrue(result.contains(postDao.getPostsByPoster(user1.getId()).get(2)));
    }

    @Test
    public void getTimelineTest() {
        User user1 = new User(0, "Tom", "Tom");
        User user2 = new User(1, "Jeff", "Jeff");

        tx.begin();
        userDao.createUser(user1);
        userDao.createUser(user2);
        tx.commit();

        user1.follow(user2);
        tx.begin();
        userDao.follow(
                userDao.getUser(user1.getId()).getId(),
                userDao.getUser(user2.getId()).getId());
        tx.commit();

        tx.begin();
        postDao.createNewPost(user1.getId(), "Content1");
        postDao.createNewPost(user1.getId(), "Content2");
        postDao.createNewPost(user2.getId(), "Content3");
        postDao.createNewPost(user1.getId(), "Content4");
        postDao.createNewPost(user1.getId(), "Content5");
        postDao.createNewPost(user2.getId(), "Content6");
        tx.commit();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date date = cal.getTime();
        tx.begin();
        postDao.createPost(user2.getId(), "Content7", date);
        postDao.createPost(user1.getId(), "Content8", date);
        postDao.createPost(user1.getId(), "Content9", date);
        tx.commit();

        List<Post> result = postDao.getTimeline(user1.getId(), 7);
        assertEquals(result.size(), 7);
    }

    @Test
    public void searchTimeTest() {
        User user1 = new User(0, "Tom", "Tom");
        User user2 = new User(1, "Jeff", "Jeff");

        tx.begin();
        userDao.createUser(user1);
        userDao.createUser(user2);
        tx.commit();

        tx.begin();
        postDao.createNewPost(user1.getId(), "asfdsfs");
        postDao.createNewPost(user1.getId(), "fassf");
        postDao.createNewPost(user2.getId(), "dfsfds");
        postDao.createNewPost(user1.getId(), "gdfgfd");
        postDao.createNewPost(user1.getId(), "bfgbfdb");
        postDao.createNewPost(user2.getId(), "qwqweqe");
        tx.commit();
        
        List<Post> result = postDao.searchPosts("b");
        assertEquals(result.size(), 1);
        
        result = postDao.searchPosts("ef");
        assertEquals(result.size(), 2);
    }
}
