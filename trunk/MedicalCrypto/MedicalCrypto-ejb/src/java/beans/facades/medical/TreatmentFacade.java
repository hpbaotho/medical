/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.medical;

import entities.medical.Treatment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Piotrek
 */
@Stateless
public class TreatmentFacade implements TreatmentFacadeLocal {
    @PersistenceContext(unitName="Medical-adminmedPU")
    private EntityManager em;

    public void create(Treatment treatment) {
        em.persist(treatment);
    }

    public void edit(Treatment treatment) {
        em.merge(treatment);
    }

    public void remove(Treatment treatment) {
        em.remove(em.merge(treatment));
    }

    public Treatment find(Object id) {
        return em.find(entities.medical.Treatment.class, id);
    }

    public List<Treatment> findAll() {
        return em.createQuery("select object(o) from Treatment as o").getResultList();
    }
    
    public void refresh(Treatment toRefresh){
        em.refresh(toRefresh);
    }

}
