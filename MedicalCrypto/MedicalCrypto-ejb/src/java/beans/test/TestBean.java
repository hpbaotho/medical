/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.test;

import beans.stateless.PersonsLocal;
import beans.stateless.TreatmentLocal;
import beans.stateless.VisitLocal;
import entities.medical.dto.PersonsDTO;
import entities.medical.dto.TreatmentDTO;
import entities.medical.dto.VisitDTO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Piotrek
 */
@Stateful
public class TestBean implements TestRemote {

    @EJB
    private VisitLocal visitBean;
    @EJB
    private TreatmentLocal treatmentBean;
    @EJB
    private PersonsLocal personsBean;

    public boolean addPerson(PersonsDTO person) {
        try {
            return personsBean.createPerson(person);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean editPerson(PersonsDTO personToEditDTO) {
        try {
            return personsBean.editPerson(personToEditDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<PersonsDTO> findPersonByZip(int zip) {
        try {
            return personsBean.findPersonByZip(zip);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public PersonsDTO findPersonById(BigInteger idPerson) {
        try {
            return personsBean.findPersonById(idPerson);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean addVisit(VisitDTO visitToAddDTO, BigInteger idPatient, BigInteger idDoctor) {
        try {
            return visitBean.createVisit(visitToAddDTO, idPatient, idDoctor);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean editVisit(VisitDTO visitToAddDTO) {
        try {
            return visitBean.editVisit(visitToAddDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<VisitDTO> findVisitByPatient(BigInteger idPatient) {
        List<VisitDTO> result = new ArrayList<VisitDTO>();
        try {
            result = visitBean.findVisitByPatient(idPatient);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean removeVisit(BigInteger idVisit) {
        try {
            return visitBean.removeVisit(idVisit);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean addTreatment(TreatmentDTO treatmentToAddDTO, BigInteger idVisit) {
        try {
            return treatmentBean.createTreatment(treatmentToAddDTO, idVisit);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean editTreatment(TreatmentDTO treatmentToEditDTO) {
        try {
            return treatmentBean.editTreatment(treatmentToEditDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) {
        List<TreatmentDTO> result = new ArrayList<TreatmentDTO>();
        try {
            result = treatmentBean.findTreatmentByVisit(idVisit);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    public List<TreatmentDTO> findTreatmentByPatient(BigInteger idPatient) {
        List<TreatmentDTO> result = new ArrayList<TreatmentDTO>();
        try {
            result = treatmentBean.findTreatmentByPatient(idPatient);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

    public boolean removeTreatment(BigInteger idTreatment) {
        try {
            return treatmentBean.removeTreatment(idTreatment);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
