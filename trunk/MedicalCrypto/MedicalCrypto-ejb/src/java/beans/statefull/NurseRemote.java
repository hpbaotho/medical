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
import exceptions.DoctorRemoveException;
import exceptions.PersonsPeselException;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Piotrek
 */
@Remote
public interface NurseRemote {
    
    boolean editPerson(PersonsDTO personToEditDTO) throws CryptographyException, PersonsPeselException;
    
    boolean createPerson(PersonsDTO personToAddDTO)throws CryptographyException, DatabaseException, PersonsPeselException;
    
    boolean removePatinet(BigInteger idPatient);
    
    boolean removeDoctor(BigInteger idDoctor) throws DoctorRemoveException;
    
    List<DoctorDTO> findDoctors() throws CryptographyException;
    
    List<PersonsDTO> findPersonByInitials(String name, String surname) throws CryptographyException;
    
    List<PersonsDTO> findPersonByZip(int zip) throws CryptographyException;
    
    PersonsDTO findPersonByPesel(BigInteger pesel) throws CryptographyException;
    
    PersonsDTO findMe() throws CryptographyException, DatabaseException;
    
    boolean editVisitDoctor(VisitDTO visitToEditDTO, BigInteger idDoctor);
    
    boolean editVisitPatient(VisitDTO visitToEditDTO, BigInteger idPatient);
    
    List<VisitDTO> findVisitByDoctor(BigInteger idDoctor) throws CryptographyException;
    
    List<VisitDTO> findVisitByPatient(BigInteger idPatient) throws CryptographyException;
    
    List<VisitDTO> findVisitByDoctorPatient(BigInteger idDoctor, BigInteger idPatient) throws CryptographyException;
    
    List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) throws CryptographyException;
    
    public List<TreatmentDTO> findTreatmentByPatient(BigInteger idPatient) throws CryptographyException;
       
}
