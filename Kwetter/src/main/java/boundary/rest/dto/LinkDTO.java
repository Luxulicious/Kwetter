/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest.dto;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class LinkDTO {

    public String link;
    public String rel;

    public LinkDTO(String link, String rel) {
        this.link = link;
        this.rel = rel;
    }

    public LinkDTO() {
    }

}
