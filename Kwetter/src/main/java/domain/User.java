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

    //<editor-fold defaultstate="collapsed" desc="Fields">
    //<editor-fold defaultstate="collapsed" desc="Non-navigational fields">
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
    //</editor-fold>   
    //<editor-fold defaultstate="collapsed" desc="Navigational fields">
    @ManyToOne()
    private Role role;

    @OneToMany(mappedBy = "poster")
    private List<Post> posts;

    @ManyToMany(/*cascade = CascadeType.ALL*/)
    private List<User> following;

    @ManyToMany(mappedBy = "following"/*, cascade = CascadeType.ALL*/)
    private List<User> followers;
    //</editor-fold>
    //</editor-fold> 

    public User() {
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
        if(user.followers.contains(this))
            following.remove(this);
    }
}
