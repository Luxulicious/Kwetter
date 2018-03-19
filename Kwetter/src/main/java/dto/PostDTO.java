/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import com.google.gson.annotations.Expose;
import domain.Post;
import domain.User;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    
    public PostDTO(Post post) {
        this.id = post.getId();
        this.content = post.getContent();
        this.date = post.getDate();
        this.poster = new UserDTO(post.getPoster());
    }
    
}
