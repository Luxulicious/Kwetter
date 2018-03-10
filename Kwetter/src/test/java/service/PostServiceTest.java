/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PostDao;
import dao.RoleDao;
import dao.UserDao;
import domain.Post;
import domain.User;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Tom
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @Mock
    private PostDao postDaoDaoService;
    @Mock
    private UserDao userDaoExh;
    @Mock
    private RoleDao roleDaoExh;
    @InjectMocks
    private ServiceExceptionHandler exh;
    @InjectMocks
    private PostService postService;

    private List<User> users;
    private List<Post> posts;

    public PostServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        postService = new PostService();
        postService.setPostDao(postDaoDaoService);
        exh.setUserDao(userDaoExh);
        exh.setRoleDao(roleDaoExh);
        postService.setExceptionHandler(exh);
        users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User(i, "User " + i, "password"));
        }
        posts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Post post = new Post(i, "Some message with the number: " + i, new Date());
            users.get(i).post(post);
            posts.add(post);
        }
    }

    /**
     * Test of getAllPosts method, of class PostService.
     */
    @Test
    public void getAllPostsTest() throws Exception {
        when(postDaoDaoService.getAllPosts()).thenReturn(posts);
        boolean result = postService.getAllPosts().isEmpty();
        assertFalse(result);
    }

    /**
     * Test of getPostsByPoster method, of class PostService.
     */
    @Test
    public void getPostsByPosterTest() throws Exception {
        User userSubject = users.get(0);
        when(userDaoExh.getUser(userSubject.getId())).thenReturn(userSubject);

        List<Post> expected = userSubject.getPosts();
        when(postDaoDaoService.getPostsByPoster(userSubject.getId())).thenReturn(userSubject.getPosts());
        List<Post> result = postService.getPostsByPoster(userSubject.getId());
        assertSame(result, expected);
    }

    /**
     * Test of getRecentPostsByPoster method, of class PostService.
     */
    @Test
    public void getRecentPostsByPosterTest() throws Exception {
        User userSubject = users.get(0);
        when(userDaoExh.getUser(userSubject.getId())).thenReturn(userSubject);

        int limit = 10;
        List<Post> expected = userSubject.getPosts();
        when(postDaoDaoService.getRecentPostsByPoster(userSubject.getId(), limit)).thenReturn(expected);
        List<Post> result = postService.getRecentPostsByPoster(userSubject.getId(), limit);
        assertSame(result, expected);
    }

    /**
     * Test of getPostCountByPoster method, of class PostService.
     */
    @Test
    public void getPostCountByPosterTest() throws Exception {
        User userSubject = users.get(0);
        when(userDaoExh.getUser(userSubject.getId())).thenReturn(userSubject);

        Long expected = Long.valueOf(userSubject.getPosts().size());
        when(postDaoDaoService.getPostCountByPoster(userSubject.getId())).thenReturn(expected);
        Long result = postService.getPostCountByPoster(userSubject.getId());
        assertSame(result, expected);
    }

    /**
     * Test of getPostsByQuery method, of class PostService.
     */
    @Test
    public void getPostsByQueryTest() throws Exception {
        fail("Yet to be implemented.");
    }

    /**
     * Test of getTimeline method, of class PostService.
     */
    @Test
    public void getTimelineTest() throws Exception {
        User userSubject1 = users.get(0);
        when(userDaoExh.getUser(userSubject1.getId())).thenReturn(userSubject1);
        User userSubject2 = users.get(1);
        when(userDaoExh.getUser(userSubject2.getId())).thenReturn(userSubject2);

        userSubject1.follow(userSubject2);
        List<Post> expected = userSubject1.getPosts();
        expected.addAll(userSubject2.getPosts());
        when(postDaoDaoService.getTimeline(userSubject1.getId())).thenReturn(expected);
        List<Post> result = postService.getTimeline(userSubject1.getId());
        assertSame(result, expected);
    }

    /**
     * Test of createPost method, of class PostService.
     */
    @Test
    public void createPostTest() throws Exception {
        User userSubject = users.get(0);
        when(userDaoExh.getUser(userSubject.getId())).thenReturn(userSubject);
        
        Post postToAdd = new Post(31478941, "Some message to add.", new Date());
        doNothing().when(postDaoDaoService).createPost(userSubject.getId(), postToAdd.getContent());
        postService.createPost(userSubject.getId(), postToAdd.getContent());
    }

}
