/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Group;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Stateless
public class GroupDao {

    @PersistenceContext
    EntityManager em;

    public GroupDao() {
    }

    public void createGroup(Group group) {
        em.persist(group);
    }

    public void updateGroup(Group group) {
        em.merge(group);
    }

    public Group getGroup(String groupName) {
        return em.find(Group.class, groupName);
    }
}
