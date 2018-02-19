/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tom
 * @email
 * @version 0.0.1
 */
public class PostDao {

    @PersistenceContext
    EntityManager em;
}
