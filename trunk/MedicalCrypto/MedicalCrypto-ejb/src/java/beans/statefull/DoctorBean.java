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
@Stateful(name = "DoctorBean", mappedName = "ejb/DoctorBean")
@RolesAllowed(value = {"doctor"})
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

    public boolean createVisit(VisitDTO visitToAddDTO, BigInteger idPatient) throws CryptographyException, DatabaseException {
        return visitBean.createVisit(visitToAddDTO, idPatient, personsBean.getLoggedUserId());
    }

    public boolean editVisit(VisitDTO visitToEditDTO) throws CryptographyException {
        return visitBean.editVisit(visitToEditDTO);
    }
    
    public boolean removeVisit(VisitDTO visitToRemoveDTO){
        return visitBean.removeVisit(visitToRemoveDTO.getIdVisit());
    }

    public List<VisitDTO> findVisitByDoctorPatient(BigInteger idDoctor, BigInteger idPatient) throws CryptographyException {
        return visitBean.findVisitByDoctorPatient(idDoctor, idPatient);
    }

    public List<VisitDTO> findVisitByDoctor() throws CryptographyException {
        return visitBean.findVisitByDoctor(personsBean.getLoggedUserId());
    }

    public List<VisitDTO> findVisitByPatient(BigInteger idPatient) throws CryptographyException {
        return visitBean.findVisitByPatient(idPatient);
    }

    public boolean createTreatment(TreatmentDTO treatmentToAddDTO, BigInteger idVisit) throws CryptographyException, DatabaseException {
        return treatmentBean.createTreatment(treatmentToAddDTO, idVisit);
    }

    public boolean editTreatment(TreatmentDTO treatmentToEditDTO) throws CryptographyException {
        return treatmentBean.editTreatment(treatmentToEditDTO);
    }
    
    public boolean removeTreatment(TreatmentDTO treatmentToRemoveDTO){
        return treatmentBean.removeTreatment(treatmentToRemoveDTO.getIdTreatment());
    }

    public List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) throws CryptographyException {
        return treatmentBean.findTreatmentByVisit(idVisit);
    }

    public List<TreatmentDTO> findTreatmentByPatient(BigInteger idPatient) throws CryptographyException {
        return treatmentBean.findTreatmentByPatient(idPatient);
    }
}
