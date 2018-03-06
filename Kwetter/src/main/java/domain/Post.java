/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "KwetterPost")
@NamedQueries({
    @NamedQuery(name = "Post.getPostsByPoster",
            query = "SELECT p FROM Post p WHERE p.poster_id = :poster_id")
    ,
@NamedQuery(name = "Post.getPostCountByPoster",
            query = "SELECT COUNT(p) FROM Post p WHERE p.poster_id = :poster_id")
    ,
@NamedQuery(name = "Post.getRecentPostsByPoster",
            query = "SELECT p FROM Post p WHERE p.poster_id = :poster_id ORDER BY p.date DESC LIMIT :limit")
    ,
@NamedQuery(name = "Post.getAllPosts",
            query = "SELECT p FROM Post")
    ,
@NamedQuery(name = "Post.getTimeline",
            query = "SELECT p FROM Post p "
            + "INNER JOIN Follow f ON f.following_id = p.poster_id "
            + "WHERE f.followers_id = :follower")
})
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 1, max = 140)
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne(cascade = CascadeType.ALL)
    private User poster;

    public Post() {
    }

    public Post(long id, String content, Date date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public Post(long id, String content, Date date, User poster) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.poster = poster;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

}
