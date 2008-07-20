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
    @PersistenceContext(unitName="Crypto-admincryptoPU")
    private EntityManager em;

    public void create(Engine engine) {
        em.persist(engine);
    }

    public void edit(Engine engine) {
        em.merge(engine);
    }

    public void remove(Engine engine) {
        em.remove(em.merge(engine));
    }

    public Engine find(Object id) {
        return em.find(entities.crypto.Engine.class, id);
    }

    public List<Engine> findAll() {
        return em.createQuery("select object(o) from Engine as o").getResultList();
    }

}
