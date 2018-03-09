/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Role;
import domain.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
@Stateless
public class RoleDao {

    @PersistenceContext
    EntityManager em;

    @Inject
    UserDao userDao;

    public List<Role> getAllRoles() {
        return em.createNamedQuery("Role.getAllRoles").getResultList();
    }

    public void setUserRole(String roleName, long userId) {
        User user = userDao.getUser(userId);
        user.setRole(new Role(roleName));
        em.merge(user);
    }
    
    public Role getRole(String roleName)
    {
        return em.find(Role.class, roleName);
    }
}
