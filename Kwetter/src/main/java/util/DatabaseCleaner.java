/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Riccardo
 */

import domain.*;
import java.sql.SQLException;
import javax.persistence.EntityManager;


public class DatabaseCleaner {

    private static final Class<?>[] ENTITY_TYPES = {
        User.class,
        Role.class,
        Post.class
    };
    
    private final EntityManager em;

    public DatabaseCleaner(EntityManager entityManager) {
        em = entityManager;
    }

    public void clean() throws SQLException {
        em.getTransaction().begin();

        for (Class<?> entityType : ENTITY_TYPES) {
            String entityName = em.getMetamodel().entity(entityType).getName();
            em.createQuery("delete from " + entityName).executeUpdate();
        }
        em.getTransaction().commit();
        em.close();
    }
}
