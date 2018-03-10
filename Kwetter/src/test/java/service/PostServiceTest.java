/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.PostDao;
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
import service.exceptions.ServiceExceptionHandler;

/**
 *
 * @author Tom
 */
@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @Mock
    private PostDao postDao;
    @Mock
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
        postService.setPostDao(postDao);
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
        when(postDao.getAllPosts()).thenReturn(posts);
        boolean result = postService.getAllPosts().isEmpty();
        assertFalse(result);
    }

    /**
     * Test of getPostsByPoster method, of class PostService.
     */
    @Test
    public void getPostsByPosterTest() throws Exception {
        List<Post> expected = users.get(0).getPosts();
        when(postDao.getPostsByPoster(users.get(0).getId())).thenReturn(users.get(0).getPosts());
        List<Post> result = postService.getPostsByPoster(users.get(0).getId());
        assertSame(result, expected);
    }

    /**
     * Test of getRecentPostsByPoster method, of class PostService.
     */
    @Test
    public void getRecentPostsByPosterTest() throws Exception {
        int limit = 10;
        List<Post> expected = users.get(0).getPosts();
        when(postDao.getRecentPostsByPoster(users.get(0).getId(), limit)).thenReturn(expected);
        List<Post> result = postService.getRecentPostsByPoster(users.get(0).getId(), limit);
        assertSame(result, expected);
    }

    /**
     * Test of getPostCountByPoster method, of class PostService.
     */
    @Test
    public void getPostCountByPosterTest() throws Exception {
        Long expected = Long.valueOf(users.get(0).getPosts().size());
        when(postDao.getPostCountByPoster(users.get(0).getId())).thenReturn(expected);
        Long result = postService.getPostCountByPoster(users.get(0).getId());
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
        users.get(0).follow(users.get(1));
        List<Post> expected = users.get(0).getPosts();
        expected.addAll(users.get(1).getPosts());
        when(postDao.getTimeline(users.get(0).getId())).thenReturn(expected);
        List<Post> result = postService.getTimeline(users.get(0).getId());
        assertSame(result, expected);
    }

    /**
     * Test of createPost method, of class PostService.
     */
    @Test
    public void createPostTest() throws Exception {
        Post postToAdd = new Post(31478941, "Some message to add.", new Date());
        doNothing().when(postDao).createPost(users.get(0).getId(), postToAdd.getContent());
        postService.createPost(users.get(0).getId(), postToAdd.getContent());
    }

}
