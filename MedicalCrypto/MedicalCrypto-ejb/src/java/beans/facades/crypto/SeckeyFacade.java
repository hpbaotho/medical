/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.crypto;

import entities.crypto.Seckey;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Piotrek
 */
@Stateless
public class SeckeyFacade implements SeckeyFacadeLocal {
    @PersistenceContext(unitName="Crypto-keyproviderPU")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Seckey find(Object id) {
        return em.find(entities.crypto.Seckey.class, id);
    }

    public List<Seckey> findAll() {
        return em.createQuery("select object(o) from Seckey as o").getResultList();
    }
    
    public void refresh(Seckey toRefresh){
        em.refresh(toRefresh);
    }

}
