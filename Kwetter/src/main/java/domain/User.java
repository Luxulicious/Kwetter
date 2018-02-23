/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    @Size(min = 1, max = 32)
    private String username;

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

    @ManyToOne()
    private Role role;

    @OneToMany(mappedBy = "poster")
    private List<Post> posts;

    @ManyToMany(/*cascade = CascadeType.ALL*/)
    private List<User> following;

    @ManyToMany(mappedBy = "following"/*, cascade = CascadeType.ALL*/)
    private List<User> followers;

    public User() {
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

    public void setRole(Role role) {
        this.role = role;
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
        if (!following.contains(user)) {
            following.add(user);
        }
        if (!user.followers.contains(this)) {
            user.followers.add(this);
        }
    }

    public void unfollow(User user) {
        if (following.contains(user)) {
            following.remove(user);
        }
        if (user.followers.contains(this)) {
            following.remove(this);
        }
    }
}
