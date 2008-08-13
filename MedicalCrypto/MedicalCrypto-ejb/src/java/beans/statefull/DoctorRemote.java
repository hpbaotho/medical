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
public interface DoctorRemote {

    boolean editDoctor(PersonsDTO personToEdit) throws DatabaseException, CryptographyException, PersonsPeselException;

    List<DoctorDTO> findDoctors() throws CryptographyException;

    PersonsDTO findMe() throws CryptographyException, DatabaseException;

    boolean createVisit(VisitDTO visitToAdd, BigInteger idPatient) throws CryptographyException, DatabaseException;

    boolean editVisit(VisitDTO visitToEdit) throws CryptographyException;

    boolean removeVisit(VisitDTO visitToRemoveDTO);

    List<VisitDTO> findVisitByDoctorPatient(BigInteger idDoctor, BigInteger idPatient) throws CryptographyException;

    List<VisitDTO> findVisitByPatient(BigInteger idPatient) throws CryptographyException;

    boolean createTreatment(TreatmentDTO treatmentToAddDTO, BigInteger idVisit) throws CryptographyException, DatabaseException;

    boolean editTreatment(TreatmentDTO treatmentToEditDTO) throws CryptographyException;

    boolean removeTreatment(TreatmentDTO treatmentToRemoveDTO);

    List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) throws CryptographyException;
}
