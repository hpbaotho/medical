/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.statefull;

import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import exceptions.PersonsPeselException;
import javax.ejb.Remote;

/**
 *
 * @author Piotrek
 */
@Remote
public interface AdminRemote {
    
    boolean editPerson(PersonsDTO personToEditDTO) throws CryptographyException, PersonsPeselException;
    
    boolean addPerson(PersonsDTO personToAddDTO)throws CryptographyException, DatabaseException, PersonsPeselException;
    
    PersonsDTO findMe() throws CryptographyException, DatabaseException;
    
}
