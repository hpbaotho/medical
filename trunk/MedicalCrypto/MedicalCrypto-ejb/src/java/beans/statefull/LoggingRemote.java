/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.statefull;

import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import javax.ejb.Remote;

/**
 *
 * @author Piotrek
 */
@Remote
public interface LoggingRemote {
    
    PersonsDTO getLoggedUser() throws CryptographyException;
    
}
