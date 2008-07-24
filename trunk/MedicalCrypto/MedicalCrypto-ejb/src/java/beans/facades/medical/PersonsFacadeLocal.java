/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.medical;

import entities.medical.Persons;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface PersonsFacadeLocal {

    void create(Persons persons);

    void edit(Persons persons);

    void remove(Persons persons);

    Persons find(Object id);
    
    Persons findByPesel(BigInteger pesel);
    
    List<Persons> findByInitials(char inn, char ins);
    
    List<Persons> findByZip(int zip);

    List<Persons> findAll();

}
