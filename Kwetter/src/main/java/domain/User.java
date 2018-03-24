/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Entity
@Table(name = "KwetterUser")
@NamedQueries({
    @NamedQuery(name = "User.getAllUsers",
            query = "SELECT u FROM User u")
    ,
@NamedQuery(name = "User.getFollowers", query
            = "SELECT u "
            + "FROM User u "
            + "LEFT JOIN u.followers f "
            + "WHERE u.following = :following")
    ,
@NamedQuery(name = "User.getFollowing",
            query = "SELECT u "
            + "FROM User u "
            + "LEFT JOIN u.following f "
            + "WHERE u.followers = :follower")
    ,
@NamedQuery(name = "User.getFollowerCount",
            query = "SELECT COUNT(u.id) "
            + "FROM User u "
            + "LEFT JOIN u.followers f "
            + "WHERE u.following = :following")
    ,
@NamedQuery(name = "User.getFollowingCount",
            query = "SELECT COUNT(u.id) "
            + "FROM User u "
            + "LEFT JOIN u.following f "
            + "WHERE u.followers = :follower")
    ,
@NamedQuery(name = "User.getUserByUsername",
            query = "SELECT u "
            + "FROM User u "
            + "WHERE u.username = :username")
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Size(min = 1, max = 32)
    private String username;

    @NotNull
    @Size(min = 1, max = 32)
    private String password;

    @Size(min = 1, max = 255)
    private String bio;

    @Size(min = 1, max = 255)
    private String location;

    @Size(min = 1, max = 32)
    private String website;

    @Size(min = 1, max = 255)
    private String icon;

    @ManyToOne(cascade = CascadeType.ALL)
    private Role role;

    @OneToMany(mappedBy = "poster")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "follow")
    private List<User> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following", cascade = CascadeType.ALL)
    @JoinTable(name = "follow")
    private List<User> followers = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Role getRole() {
        return role;
    }

    public Role setRole(Role role) {
        this.role = role;
        this.role.addUserRoles(this);
        return this.role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public void follow(User user) {
        if (!user.equals(this)) {
            if (!following.contains(user)) {
                following.add(user);
            }
            if (!user.followers.contains(this)) {
                user.followers.add(this);
            }
        }
    }

    public void unfollow(User user) {
        if (following.contains(user)) {
            following.remove(user);
        }
        if (user.followers.contains(this)) {
            followers.remove(this);
        }
    }

    public Post post(Post post) {
        if (!this.posts.contains(post)) {
            post.setPoster(this);
            this.posts.add(post);
        }
        return post;
    }

    public void removePost(Post post) {
        if (this.posts.contains(post)) {
            this.posts.remove(post);
        }
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username=" + username + ", password=" + password + ", bio=" + bio + ", location=" + location + ", website=" + website + ", icon=" + icon + ", role=" + role + '}';
    }

}
