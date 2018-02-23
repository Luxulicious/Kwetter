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
public class Post implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Non-navigational fields">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 140)
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    //</editor-fold>
    
    @ManyToOne()
    private User poster;

    public Post() {
    }        
}
