/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.crypto;

import entities.crypto.Ekey;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Piotrek
 */
@Stateless
public class EkeyFacade implements EkeyFacadeLocal {
    @PersistenceContext(unitName="Crypto-keyproviderPU")
    private EntityManager em;

    public void create(Ekey ekey) {
        em.persist(ekey);
    }

    public void edit(Ekey ekey) {
        em.merge(ekey);
    }

    public void remove(Ekey ekey) {
        em.remove(em.merge(ekey));
    }

    public Ekey find(Object id) {
        return em.find(entities.crypto.Ekey.class, id);
    }

    public List<Ekey> findAll() {
        return em.createQuery("select object(o) from Ekey as o").getResultList();
    }
    
    public void refresh(Ekey toRefresh){
        em.refresh(toRefresh);
    }

}
