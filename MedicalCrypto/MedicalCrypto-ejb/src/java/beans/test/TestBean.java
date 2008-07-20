/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.test;

import beans.facades.medical.KeyManifestFacadeLocal;
import beans.facades.medical.PersonsFacadeLocal;
import beans.facades.medical.TreatmentFacadeLocal;
import beans.facades.medical.VisitFacadeLocal;
import entities.medical.KeyManifest;
import entities.medical.Persons;
import entities.medical.Treatment;
import entities.medical.Visit;
import java.math.BigInteger;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Piotrek
 */
@Stateful
public class TestBean implements TestRemote {
    @EJB
    private TreatmentFacadeLocal treatmentFacade;
    @EJB
    private VisitFacadeLocal visitFacade;

    @EJB
    private KeyManifestFacadeLocal keyManifestFacade;
    @EJB
    private PersonsFacadeLocal personsFacade;

    public boolean addPerson(Persons person, BigInteger idKeyManifest) {
        KeyManifest km = keyManifestFacade.find(idKeyManifest);
        person.setKeyManifestId(km);
        personsFacade.create(person);
        return true;
    }

    public boolean addVisit(Visit visit, BigInteger idPatient, BigInteger idDoc, BigInteger idKeyManifest) {
        Persons patient = personsFacade.find(idPatient);
        Persons doc = personsFacade.find(idDoc);
        KeyManifest km = keyManifestFacade.find(idKeyManifest);
        visit.setPatientId(patient);
        visit.setDoctorId(doc);
        visit.setKeyManifestId(km);
        visitFacade.create(visit);
        return true;
    }

    public boolean addTreatment(Treatment treatment, BigInteger idVisit, BigInteger idKeyManifest) {
        Visit v= visitFacade.find(idVisit);
        KeyManifest km = keyManifestFacade.find(idKeyManifest);
        treatment.setVisitId(v);
        treatment.setKeyManifestId(km);
        treatmentFacade.create(treatment);
        return true;
    }
    
    
}
