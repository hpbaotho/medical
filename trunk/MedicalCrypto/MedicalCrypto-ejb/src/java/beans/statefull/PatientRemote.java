/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.statefull;

import entities.medical.dto.DoctorDTO;
import entities.medical.dto.PersonsDTO;
import entities.medical.dto.TreatmentDTO;
import entities.medical.dto.VisitDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import exceptions.PersonsPeselException;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Piotrek
 */
@Remote
public interface PatientRemote {

    boolean editPatient(PersonsDTO personToEdit) throws DatabaseException, CryptographyException, PersonsPeselException;

    List<DoctorDTO> findDoctors() throws CryptographyException;

    PersonsDTO findMe() throws CryptographyException, DatabaseException;

    List<VisitDTO> findVisit() throws CryptographyException;
    
    List<VisitDTO> findVisitByDoctorPatient(BigInteger idDoctor) throws CryptographyException;

    List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) throws CryptographyException;

    List<TreatmentDTO> findTreatmentByPatient() throws CryptographyException;
}
