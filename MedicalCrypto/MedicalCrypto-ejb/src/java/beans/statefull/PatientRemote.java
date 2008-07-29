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
public interface PatientRemote {
    
    boolean editPatient(PersonsDTO personToEdit) throws DatabaseException, CryptographyException, PersonsPeselException;
    
    PersonsDTO findMe() throws CryptographyException, DatabaseException;
    
}
