/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.facades.medical;

import entities.medical.Treatment;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;

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
public class TreatmentFacade implements TreatmentFacadeLocal {

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

    public void create(Treatment treatment) {
        setem();
        em.persist(treatment);
    }

    public void edit(Treatment treatment) {
        setem();
        em.merge(treatment);
    }

    public void remove(Treatment treatment) {
        setem();
        em.remove(em.merge(treatment));
    }

    public Treatment find(Object id) {
        setem();
        return em.find(entities.medical.Treatment.class, id);
    }

    public List<Treatment> findAll() {
        setem();
        return em.createQuery("select object(o) from Treatment as o").getResultList();
    }

    public void refresh(Treatment toRefresh) {
        setem();
        em.refresh(toRefresh);
    }
}
