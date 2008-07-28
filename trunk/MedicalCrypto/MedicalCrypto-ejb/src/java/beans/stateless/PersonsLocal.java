/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.stateless;

import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import exceptions.PersonsLoginException;
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

    boolean createPerson(PersonsDTO personToAddDTO) throws PersonsLoginException, PersonsPeselException, CryptographyException, DatabaseException;
    
    boolean editPerson(PersonsDTO personToEditDTO) throws PersonsPeselException, CryptographyException;
    
    boolean removePerson(BigInteger idPersonToRemove);
    
    List<PersonsDTO> findPersonByInitials(String name, String surname) throws CryptographyException;
    
    List<PersonsDTO> findPersonByZip(int zip) throws CryptographyException;
    
    PersonsDTO findPersonById(BigInteger idPerson) throws CryptographyException;
    
    PersonsDTO findPersonByPesel(BigInteger pesel) throws CryptographyException;
    
}
