/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.statefull;

import beans.stateless.PersonsLocal;
import beans.stateless.TreatmentLocal;
import beans.stateless.VisitLocal;
import entities.medical.dto.DoctorDTO;
import entities.medical.dto.PersonsDTO;
import entities.medical.dto.TreatmentDTO;
import entities.medical.dto.VisitDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import exceptions.PersonsPeselException;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Piotrek
 */
@Stateful(name="PatientBean",mappedName="ejb/PatientBean")
@RolesAllowed(value={"patient"})
public class PatientBean implements PatientRemote {

    @EJB
    private TreatmentLocal treatmentBean;
    @EJB
    private VisitLocal visitBean;
    @EJB
    private PersonsLocal personsBean;

    public boolean editPatient(PersonsDTO personToEditDTO) throws DatabaseException, CryptographyException, PersonsPeselException {
        if (personToEditDTO.getIdPersons() != null && findMe().getIdPersons().equals(personToEditDTO.getIdPersons())) {
            return personsBean.editPerson(personToEditDTO);
        }
        return false;
    }
    
    public List<DoctorDTO> findDoctors() throws CryptographyException {
        return personsBean.findDoctors();
    }

    public PersonsDTO findMe() throws CryptographyException, DatabaseException {
        BigInteger myId = personsBean.getLoggedUserId();
        PersonsDTO result = personsBean.findPersonById(myId);
        if (result != null) {
            return result;
        }
        throw new DatabaseException();
    }

    public List<VisitDTO> findVisit() throws CryptographyException {
        return visitBean.findVisitByPatient(personsBean.getLoggedUserId());
    }
    
    public List<VisitDTO> findVisitByDoctorPatient(BigInteger idDoctor) throws CryptographyException {
        return visitBean.findVisitByDoctorPatient(idDoctor, personsBean.getLoggedUserId());
    }
    
    public List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) throws CryptographyException {
        return treatmentBean.findTreatmentByVisit(idVisit);
    }
    
    public List<TreatmentDTO> findTreatmentByPatient() throws CryptographyException {
        return treatmentBean.findTreatmentByPatient(personsBean.getLoggedUserId());
    }
    
    
}
