/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.facades.medical;

import entities.medical.Persons;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Piotrek
 */
@Stateless
@PersistenceContexts({
    @PersistenceContext(name = "patient", unitName = "Medical-patientPU"),
    @PersistenceContext(name = "doctor", unitName = "Medical-doctorPU"),
    @PersistenceContext(name = "nurse", unitName = "Medical-nursePU")
})
@DeclareRoles({"patient", "doctor", "nurse"})
public class PersonsFacade implements PersonsFacadeLocal {

    private EntityManager em;
    @Resource
    private EJBContext ctx;

    private void setem() {
        if (ctx.isCallerInRole("doctor")) {
            em = (EntityManager) ctx.lookup("doctor");
        } else if (ctx.isCallerInRole("nurse")) {
            em = (EntityManager) ctx.lookup("nurse");
        } else {
            em = (EntityManager) ctx.lookup("patient");
        }
    }

    public void create(Persons persons) {
        setem();
        em.persist(persons);
    }

    public void edit(Persons persons) {
        setem();
        em.merge(persons);
    }

    public void remove(Persons persons) {
        setem();
        em.remove(em.merge(persons));
    }

    public Persons find(Object id) {
        setem();
        return em.find(entities.medical.Persons.class, id);
    }

    public Persons findByPesel(String pesel) {
        setem();
        Query queryByPesel = em.createNamedQuery("Persons.findByPesel");
        queryByPesel.setParameter("pesel", pesel);
        try {
            return (Persons) queryByPesel.getSingleResult();
        } catch (PersistenceException ex) {
            return null;
        }
    }

    public List<Persons> findByInitials(char inn, char ins) {
        setem();
        Query queryByInitials = em.createNamedQuery("Persons.findByInitials");
        queryByInitials.setParameter("inn", inn);
        queryByInitials.setParameter("ins", ins);
        return queryByInitials.getResultList();
    }

    public List<Persons> findByZip(int zip) {
        setem();
        Query queryByZip = em.createNamedQuery("Persons.findByZip");
        queryByZip.setParameter("zip", zip);
        return queryByZip.getResultList();
    }

    public List<Persons> findByRole(String role) {
        setem();
        Query queryByZip = em.createNamedQuery("Persons.findByRole");
        queryByZip.setParameter("role", role);
        return queryByZip.getResultList();
    }

    public List<Persons> findAll() {
        setem();
        return em.createQuery("select object(o) from Persons as o").getResultList();
    }

    public void refresh(Persons toRefresh) {
        setem();
        em.refresh(toRefresh);
    }
}
