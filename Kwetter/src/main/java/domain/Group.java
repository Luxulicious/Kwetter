/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Entity
@Table(name = "KwetterGroup")
public class Group {

    public Group() {
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    @Id
    private String groupName;

    @ManyToMany
    @JoinTable(name = "USER_GROUP",
            joinColumns = @JoinColumn(name = "groupName",
                    referencedColumnName = "groupName"),
            inverseJoinColumns = @JoinColumn(name = "userName",
                    referencedColumnName = "userName"))
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
