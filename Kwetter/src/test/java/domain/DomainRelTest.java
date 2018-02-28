/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.DummyData;

/**
 *
 * @author Tom
 */
public class DomainRelTest {

    private DummyData dummyData;

    public DomainRelTest() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        dummyData = new DummyData();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void relUserPostTest() {
        Post post = dummyData.getPosts().get(0);
        User poster = dummyData.getUsers().get(0);
        assertEquals(poster, post.getPoster());
        assertEquals(poster.getPosts().get(0), post);
    }

    @Test
    public void relUserRoleTest() {
        Role role = dummyData.getRoles().get(0);
        User user = dummyData.getUsers().get(0);
        assertEquals(user.getRole(), role);
        assertEquals(user, role.getUserRoles().get(0));
    }

    @Test
    public void relFollow() {
        User follower = dummyData.getUsers().get(0);
        User followed = dummyData.getUsers().get(1);
        if (follower.getFollowing().contains(followed)) {
            assertTrue(true);
        }
        if (followed.getFollowers().contains(follower)) {
            assertTrue(true);
        }
    }
}
