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
    
    List<PersonsDTO> findByInitials(String name, String surname) throws CryptographyException;
    
    List<PersonsDTO> findByZip(int zip) throws CryptographyException;
    
    PersonsDTO findById(BigInteger idPerson) throws CryptographyException;
    
}
