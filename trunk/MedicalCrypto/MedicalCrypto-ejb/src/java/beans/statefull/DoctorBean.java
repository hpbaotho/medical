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
public class DoctorBean implements DoctorRemote {

    @EJB
    private TreatmentLocal treatmentBean;
    @EJB
    private VisitLocal visitBean;
    @EJB
    private PersonsLocal personsBean;

    public boolean editDoctor(PersonsDTO personToEditDTO) throws DatabaseException, CryptographyException, PersonsPeselException {
        if (personToEditDTO.getIdPersons() != null && findMe().getIdPersons().equals(personToEditDTO.getIdPersons())) {
            return personsBean.editPerson(personToEditDTO);
        }
        return false;
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
