/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.stateless;

import entities.medical.dto.TreatmentDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface TreatmentLocal {

    boolean createTreatment(TreatmentDTO treatmentToAddDTO, BigInteger idVisit) throws CryptographyException, DatabaseException;
    
    boolean editTreatment(TreatmentDTO treatmentToEditDTO) throws CryptographyException, DatabaseException;
    
    boolean removeTreatment(BigInteger idTreatment);
        
    List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) throws CryptographyException;
    
    List<TreatmentDTO> findTreatmentByPatient(BigInteger idPatient) throws CryptographyException;
    
}
