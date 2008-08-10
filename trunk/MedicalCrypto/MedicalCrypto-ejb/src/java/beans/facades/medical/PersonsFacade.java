/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.facades.medical;

import entities.medical.Persons;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Piotrek
 */
@Stateless
public class PersonsFacade implements PersonsFacadeLocal {

    @PersistenceContext(unitName = "Medical-adminmedPU")
    private EntityManager em;

    public void create(Persons persons) {
        em.persist(persons);
    }

    public void edit(Persons persons) {
        em.merge(persons);
    }

    public void remove(Persons persons) {
        em.remove(em.merge(persons));
    }

    public Persons find(Object id) {
        return em.find(entities.medical.Persons.class, id);
    }

    public Persons findByPesel(String pesel) {
        Query queryByPesel = em.createNamedQuery("Persons.findByPesel");
        queryByPesel.setParameter("pesel", pesel);
        try {
            return (Persons) queryByPesel.getSingleResult();
        } catch (PersistenceException ex) {
            return null;
        }
    }

    public List<Persons> findByInitials(char inn, char ins) {
        Query queryByInitials = em.createNamedQuery("Persons.findByInitials");
        queryByInitials.setParameter("inn", inn);
        queryByInitials.setParameter("ins", ins);
        return queryByInitials.getResultList();
    }

    public List<Persons> findByZip(int zip) {
        Query queryByZip = em.createNamedQuery("Persons.findByZip");
        queryByZip.setParameter("zip", zip);
        return queryByZip.getResultList();
    }

    public List<Persons> findByRole(String role) {
        Query queryByZip = em.createNamedQuery("Persons.findByRole");
        queryByZip.setParameter("role", role);
        return queryByZip.getResultList();
    }

    public List<Persons> findAll() {
        return em.createQuery("select object(o) from Persons as o").getResultList();
    }
    
    public void refresh(Persons toRefresh){
        em.refresh(toRefresh);
    }
}
