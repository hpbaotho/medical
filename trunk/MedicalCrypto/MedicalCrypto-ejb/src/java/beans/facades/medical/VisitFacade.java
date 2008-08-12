/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.medical;

import entities.medical.Visit;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Piotrek
 */
@Stateless
public class VisitFacade implements VisitFacadeLocal {
    @PersistenceContext(unitName="Medical-adminmedPU")
    private EntityManager em;

    public void create(Visit visit) {
        em.persist(visit);
    }

    public void edit(Visit visit) {
        em.merge(visit);
    }

    public void remove(Visit visit) {
        em.remove(em.merge(visit));
        }

    public Visit find(Object id) {
        return em.find(entities.medical.Visit.class, id);
    }

    public List<Visit> findAll() {
        return em.createQuery("select object(o) from Visit as o").getResultList();
    }
    
    public void refresh(Visit toRefresh){
        em.refresh(toRefresh);
    }

}
