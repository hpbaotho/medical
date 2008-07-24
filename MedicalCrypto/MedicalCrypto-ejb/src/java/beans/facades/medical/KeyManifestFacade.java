/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.medical;

import entities.medical.KeyManifest;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Piotrek
 */
@Stateless
public class KeyManifestFacade implements KeyManifestFacadeLocal {
    @PersistenceContext(unitName="Medical-adminmedPU")
    private EntityManager em;

    public void create(KeyManifest keyManifest) {
        em.persist(keyManifest);
    }

    public void edit(KeyManifest keyManifest) {
        em.merge(keyManifest);
    }

    public void remove(KeyManifest keyManifest) {
        em.remove(em.merge(keyManifest));
    }

    public KeyManifest find(Object id) {
        return em.find(entities.medical.KeyManifest.class, id);
    }
    
    public List<KeyManifest> findByFamilyStatus(String family, String status) {
        Query queryByFamilyStatus = em.createNamedQuery("KeyManifest.findByKeyFamilyStatus");
        queryByFamilyStatus.setParameter("keyFamily", family);
        queryByFamilyStatus.setParameter("status", status);
        return queryByFamilyStatus.getResultList();
    }

    public List<KeyManifest> findAll() {
        return em.createQuery("select object(o) from KeyManifest as o").getResultList();
    }

}
