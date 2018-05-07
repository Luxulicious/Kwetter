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
public class RegistrationDTO {

    public String username;
    public String password;

    public RegistrationDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public RegistrationDTO() {
    }

}
