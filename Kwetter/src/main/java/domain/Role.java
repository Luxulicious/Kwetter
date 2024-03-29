/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Entity
@Table(name = "KwetterRole")
@NamedQueries({
    @NamedQuery(name = "Role.getAllRoles",
            query = "SELECT r FROM Role r")
})
public class Role implements Serializable {

    @Id
    private String roleName;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> userRoles = new ArrayList<>();

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
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

    public void addUserRoles(User user) {
        if (!userRoles.contains(user)) {
            userRoles.add(user);
        }
    }
}
