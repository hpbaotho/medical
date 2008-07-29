/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.statefull;

import beans.stateless.PersonsLocal;
import beans.stateless.TreatmentLocal;
import beans.stateless.VisitLocal;
import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import exceptions.PersonsPeselException;
import java.math.BigInteger;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Piotrek
 */
@Stateful
public class AdminBean implements AdminRemote {
    
    @EJB
    private TreatmentLocal treatmentBean;
    @EJB
    private VisitLocal visitBean;
    @EJB
    private PersonsLocal personsBean;
    
    public boolean editPerson(PersonsDTO personToEditDTO) throws CryptographyException, PersonsPeselException {
        return personsBean.editPerson(personToEditDTO);
    }
    
    public boolean addPerson(PersonsDTO personToAddDTO)throws CryptographyException, DatabaseException, PersonsPeselException {
        return personsBean.createPerson(personToAddDTO);
    }
    
    public PersonsDTO findMe() throws CryptographyException, DatabaseException {
        BigInteger myId = personsBean.getLoggedUserId();
        PersonsDTO result = personsBean.findPersonById(myId);
        if (result != null) {
            return result;
        }
        throw new DatabaseException();
    }
    
}
