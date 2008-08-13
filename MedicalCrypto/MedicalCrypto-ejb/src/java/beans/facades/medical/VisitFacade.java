/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.facades.medical;

import entities.medical.Visit;
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
public class VisitFacade implements VisitFacadeLocal {

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

    public void create(Visit visit) {
        setem();
        em.persist(visit);
    }

    public void edit(Visit visit) {
        setem();
        em.merge(visit);
    }

    public void remove(Visit visit) {
        setem();
        em.remove(em.merge(visit));
    }

    public Visit find(Object id) {
        setem();
        return em.find(entities.medical.Visit.class, id);
    }

    public List<Visit> findAll() {
        setem();
        return em.createQuery("select object(o) from Visit as o").getResultList();
    }

    public void refresh(Visit toRefresh) {
        setem();
        em.refresh(toRefresh);
    }
}
