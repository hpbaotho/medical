/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.test;

import entities.medical.dto.PersonsDTO;
import entities.medical.dto.TreatmentDTO;
import entities.medical.dto.VisitDTO;
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

    boolean editPerson(PersonsDTO personToEditDTO);

    List<PersonsDTO> findPersonByZip(int zip);
    
    PersonsDTO findPersonById(BigInteger idPerson);

    boolean addVisit(VisitDTO visitToAddDTO, BigInteger idPatient, BigInteger idDoctor);

    boolean editVisit(VisitDTO visitToAddDTO);

    boolean removeVisit(BigInteger idVisit);

    List<VisitDTO> findVisitByBatient(BigInteger idPatient);

    boolean addTreatment(TreatmentDTO treatmentToAddDTO, BigInteger idVisit);

    boolean editTreatment(TreatmentDTO treatmentToEditDTO);

    boolean removeTreatment(BigInteger idTreatment);

    List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit);

    List<TreatmentDTO> findTreatmentByPatient(BigInteger idPatient);
}
