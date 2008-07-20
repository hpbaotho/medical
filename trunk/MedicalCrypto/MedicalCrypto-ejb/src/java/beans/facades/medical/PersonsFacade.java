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

/**
 *
 * @author Piotrek
 */
@Stateless
public class PersonsFacade implements PersonsFacadeLocal {
    @PersistenceContext(unitName="Medical-adminmedPU")
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

    public List<Persons> findAll() {
        return em.createQuery("select object(o) from Persons as o").getResultList();
    }

}
