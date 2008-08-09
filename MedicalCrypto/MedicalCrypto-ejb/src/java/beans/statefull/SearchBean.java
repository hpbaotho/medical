/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.statefull;

import beans.stateless.PersonsLocal;
import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Piotrek
 */
@Stateful(name="SearchBean", mappedName="ejb/SearchBean")
@RolesAllowed(value={"admin","nurse","doctor"})
public class SearchBean implements SearchRemote {
    @EJB
    private PersonsLocal personsBean;
    
    public List<PersonsDTO> findPersonByInitials(String name, String surname) throws CryptographyException {
        return personsBean.findPersonByInitials(name, surname);
    }

    public PersonsDTO findPersonByPesel(String pesel) throws CryptographyException {
        return personsBean.findPersonByPesel(pesel);
    }
    
}
