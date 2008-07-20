/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.medical;

import entities.medical.Treatment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface TreatmentFacadeLocal {

    void create(Treatment treatment);

    void edit(Treatment treatment);

    void remove(Treatment treatment);

    Treatment find(Object id);

    List<Treatment> findAll();

}
