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
import exceptions.DoctorRemoveException;
import exceptions.PersonsPeselException;
import java.math.BigInteger;
import java.util.List;
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

    public boolean createPerson(PersonsDTO personToAddDTO) throws CryptographyException, DatabaseException, PersonsPeselException {
        return personsBean.createPerson(personToAddDTO);
    }

    public boolean editPerson(PersonsDTO personToEditDTO) throws CryptographyException, PersonsPeselException {
        return personsBean.editPerson(personToEditDTO);
    }

    public boolean removePatinet(BigInteger idPatient) {
        return personsBean.removePatient(idPatient);
    }

    public boolean removeDoctor(BigInteger idDoctor) throws DoctorRemoveException {
        return personsBean.removeDoctor(idDoctor);
    }

    public List<DoctorDTO> findDoctors() throws CryptographyException {
        return personsBean.findDoctors();
    }

    public List<PersonsDTO> findPersonByInitials(String name, String surname) throws CryptographyException {
        return personsBean.findPersonByInitials(name, surname);
    }

    public List<PersonsDTO> findPersonByZip(int zip) throws CryptographyException {
        return personsBean.findPersonByZip(zip);
    }

    public PersonsDTO findPersonByPesel(BigInteger pesel) throws CryptographyException {
        return personsBean.findPersonByPesel(pesel);
    }

    public PersonsDTO findMe() throws CryptographyException, DatabaseException {
        BigInteger myId = personsBean.getLoggedUserId();
        PersonsDTO result = personsBean.findPersonById(myId);
        if (result != null) {
            return result;
        }
        throw new DatabaseException();
    }

    public boolean editVisit(VisitDTO visitToEditDTO) throws CryptographyException {
        return visitBean.editVisit(visitToEditDTO);
    }

    public boolean editVisitDoctor(VisitDTO visitToEditDTO, BigInteger idDoctor) {
        return visitBean.editVisitDoctor(visitToEditDTO, idDoctor);
    }

    public boolean editVisitPatient(VisitDTO visitToEditDTO, BigInteger idPatient) {
        return visitBean.editVisitPatient(visitToEditDTO, idPatient);
    }

    public List<VisitDTO> findVisitByDoctor(BigInteger idDoctor) throws CryptographyException {
        return visitBean.findVisitByDoctor(idDoctor);
    }

    public List<VisitDTO> findVisitByPatient(BigInteger idPatient) throws CryptographyException {
        return visitBean.findVisitByPatient(idPatient);
    }

    public List<VisitDTO> findVisitByDoctorPatient(BigInteger idDoctor, BigInteger idPatient) throws CryptographyException {
        return visitBean.findVisitByDoctorPatient(idDoctor, idPatient);
    }

    public List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) throws CryptographyException {
        return treatmentBean.findTreatmentByVisit(idVisit);
    }

    public List<TreatmentDTO> findTreatmentByPatient(BigInteger idPatient) throws CryptographyException {
        return treatmentBean.findTreatmentByPatient(idPatient);
    }
}
