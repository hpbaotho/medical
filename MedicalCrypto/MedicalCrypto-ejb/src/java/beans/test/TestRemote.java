/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.test;

import entities.medical.Persons;
import entities.medical.Treatment;
import entities.medical.Visit;
import java.math.BigInteger;
import javax.ejb.Remote;

/**
 *
 * @author Piotrek
 */
@Remote
public interface TestRemote {
    
    boolean addPerson(Persons person, BigInteger idKeyManifest);
    
    boolean addVisit(Visit visit, BigInteger idPatient, BigInteger idDoc, BigInteger idKeyManifest);

    boolean addTreatment(Treatment treatment, BigInteger idVisit, BigInteger idKeyManifest);
    
}
