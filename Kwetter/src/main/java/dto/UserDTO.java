/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import com.google.gson.annotations.Expose;
import domain.Post;
import domain.Role;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class UserDTO {

    public long id;
    public String username;
    public String password;
    public String bio;
    public String location;
    public String website;
    public String icon;
    public RoleDTO role;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.bio = user.getBio();
        this.location = user.getLocation();
        this.website = user.getWebsite();
        this.icon = user.getIcon();
        this.role = new RoleDTO(user.getRole());
    }
}
