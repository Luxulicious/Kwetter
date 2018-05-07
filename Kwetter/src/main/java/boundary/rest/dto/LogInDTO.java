/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary.rest.dto;

import domain.User;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class LogInDTO {

    public String username;
    public String password;

    public LogInDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    public LogInDTO() {
    }

}
