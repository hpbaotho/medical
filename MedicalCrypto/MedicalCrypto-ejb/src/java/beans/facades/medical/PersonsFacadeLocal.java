/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.medical;

import entities.medical.Persons;
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

    List<Persons> findAll();

}
