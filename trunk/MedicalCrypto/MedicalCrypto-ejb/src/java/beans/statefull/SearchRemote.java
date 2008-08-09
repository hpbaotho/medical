/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.statefull;

import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Piotrek
 */
@Remote
public interface SearchRemote {
    
    List<PersonsDTO> findPersonByInitials(String name, String surname) throws CryptographyException;

    PersonsDTO findPersonByPesel(String pesel) throws CryptographyException;
    
    
    
}
