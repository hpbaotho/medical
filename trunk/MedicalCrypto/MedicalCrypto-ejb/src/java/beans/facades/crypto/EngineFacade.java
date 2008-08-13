/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.crypto;

import entities.crypto.Engine;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Piotrek
 */
@Stateless
public class EngineFacade implements EngineFacadeLocal {
    @PersistenceContext(unitName="Crypto-keyproviderPU")
    private EntityManager em;

    public Engine find(Object id) {
        return em.find(entities.crypto.Engine.class, id);
    }

    public List<Engine> findAll() {
        return em.createQuery("select object(o) from Engine as o").getResultList();
    }
    
    public void refresh(Engine toRefresh){
        em.refresh(toRefresh);
    }

}
