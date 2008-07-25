/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.test;

import entities.medical.Treatment;
import entities.medical.Visit;
import entities.medical.dto.PersonsDTO;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Piotrek
 */
@Remote
public interface TestRemote {
    
    boolean addPerson(PersonsDTO person);
    
    boolean addVisit(Visit visit, BigInteger idPatient, BigInteger idDoc, BigInteger idKeyManifest);

    boolean addTreatment(Treatment treatment, BigInteger idVisit, BigInteger idKeyManifest);
    
    List<PersonsDTO> findPersonByZip(int zip);
    
    boolean editPerson(PersonsDTO personToEditDTO);
    
}
