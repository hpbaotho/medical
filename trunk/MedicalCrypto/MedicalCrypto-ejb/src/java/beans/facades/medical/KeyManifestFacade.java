/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.facades.medical;

import entities.medical.KeyManifest;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
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
public class KeyManifestFacade implements KeyManifestFacadeLocal {

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

    public void create(KeyManifest keyManifest) {
        setem();
        em.persist(keyManifest);
    }

    public void edit(KeyManifest keyManifest) {
        setem();
        em.merge(keyManifest);
    }

    public void remove(KeyManifest keyManifest) {
        setem();
        em.remove(em.merge(keyManifest));
    }

    public KeyManifest find(Object id) {
        setem();
        return em.find(entities.medical.KeyManifest.class, id);
    }

    public List<KeyManifest> findByFamilyStatus(String family, String status) {
        setem();
        Query queryByFamilyStatus = em.createNamedQuery("KeyManifest.findByKeyFamilyStatus");
        queryByFamilyStatus.setParameter("keyFamily", family);
        queryByFamilyStatus.setParameter("status", status);
        return queryByFamilyStatus.getResultList();
    }

    public List<KeyManifest> findAll() {
        setem();
        return em.createQuery("select object(o) from KeyManifest as o").getResultList();
    }

    public void refresh(KeyManifest toRefresh) {
        setem();
        em.refresh(toRefresh);
    }
}
