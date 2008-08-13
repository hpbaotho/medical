/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.statefull;

import beans.stateless.PersonsLocal;
import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Piotrek
 */
@Stateful(name="LoggingBean",mappedName="ejb/LoggingBean")
@RolesAllowed(value={"doctor","patient","nurse"})
public class LoggingBean implements LoggingRemote {
    @EJB
    private PersonsLocal personsBean;
    
    public PersonsDTO getLoggedUser() throws CryptographyException{
        return personsBean.findPersonById(personsBean.getLoggedUserId());
    }
    
}
