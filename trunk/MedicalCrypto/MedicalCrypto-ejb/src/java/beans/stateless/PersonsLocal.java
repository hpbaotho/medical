/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.stateless;

import entities.medical.dto.DoctorDTO;
import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import exceptions.DoctorRemoveException;
import exceptions.PersonsPeselException;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface PersonsLocal {

    boolean createPerson(PersonsDTO personToAddDTO) throws PersonsPeselException, CryptographyException, DatabaseException;
    
    boolean editPerson(PersonsDTO personToEditDTO) throws PersonsPeselException, CryptographyException;
    
    boolean removePatient(BigInteger idPersonToRemove);
    
    boolean removeDoctor(BigInteger idDoctorToRemove) throws DoctorRemoveException;
    
    List<PersonsDTO> findPersonByInitials(String name, String surname) throws CryptographyException;
    
    List<PersonsDTO> findPersonByZip(int zip) throws CryptographyException;
    
    PersonsDTO findPersonById(BigInteger idPerson) throws CryptographyException;
    
    PersonsDTO findPersonByPesel(BigInteger pesel) throws CryptographyException;
    
    List<DoctorDTO> findDoctors() throws CryptographyException;
    
    BigInteger getLoggedUserId();
    
}
