/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest.dto;

import domain.Post;
import java.util.Date;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class PostDTO {

    public long id;
    public String content;
    public Date date;
    public UserDTO poster;

    public LinkDTO posterUri;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.date = post.getDate();
        this.poster = new UserDTO(post.getPoster());
    }

    public PostDTO() {
    }

}
