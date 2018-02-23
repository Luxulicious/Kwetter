/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */

@Entity
@XmlRootElement
public class Role implements Serializable {

    @Id
    private String roleName;
    
    @OneToMany()
    private List<User> userRoles;

    public Role() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<User> userRoles) {
        this.userRoles = userRoles;
    }
    
    public String getName() {
        return roleName;
    }

    public void setName(String name) {
        this.roleName = name;
    }
}
