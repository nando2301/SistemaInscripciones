/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.tania.inscripciones.bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import me.tania.inscripciones.entity.Usuario;

/**
 *
 * @author 
 */
public class LoginQuery {

    public Usuario l = null;
    EntityManagerFactory emf;
    EntityManager em;

    public LoginQuery() {
        emf = Persistence.createEntityManagerFactory("WebLoginPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    public boolean loginControl(String username, String password) {
        try {
            l = em.createNamedQuery("Login.control", Usuario.class).setParameter("usuario", username).setParameter("password", password).getSingleResult();
            if (l != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
