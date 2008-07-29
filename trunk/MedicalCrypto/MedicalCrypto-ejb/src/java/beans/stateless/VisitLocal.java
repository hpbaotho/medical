/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.stateless;

import entities.medical.dto.VisitDTO;
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
public interface VisitLocal {

    boolean createVisit(VisitDTO visitToAddDTO, BigInteger idPatient, BigInteger idDoctor) throws CryptographyException, DatabaseException;

    boolean editVisit(VisitDTO visitToEditDTO) throws CryptographyException, DatabaseException;

    boolean editVisitPatient(VisitDTO visitToEditDTO, BigInteger idPatient);

    boolean editVisitDoctor(VisitDTO visitToEditDTO, BigInteger idDoctor);
    
     boolean removeVisit(BigInteger idVisit);

    List<VisitDTO> findVisitByPatient(BigInteger idPatient) throws CryptographyException;
    
    List<VisitDTO> findVisitByDoctor(BigInteger idDoctor) throws CryptographyException;
    
}
