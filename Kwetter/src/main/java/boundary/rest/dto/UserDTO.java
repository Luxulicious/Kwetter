/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest.dto;

import domain.User;
import java.util.List;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class UserDTO {

    public long id;
    public String username;
    //TODO Remove password(?)
    //public String password;
    public String bio;
    public String location;
    public String website;
    public String icon;
    public RoleDTO role;

    public LinkDTO postsUri;
    public List<LinkDTO> postUris;
    public LinkDTO followersUri;
    public List<LinkDTO> followerUris;
    public LinkDTO followingsUri;
    public List<LinkDTO> followingUris;
    public LinkDTO roleUri;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        //this.password = user.getPassword();
        this.bio = user.getBio();
        this.location = user.getLocation();
        this.website = user.getWebsite();
        this.icon = user.getIcon();
        this.role = new RoleDTO(user.getRole());
    }

    public UserDTO() {
    }

}
