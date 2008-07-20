/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.medical;

import entities.medical.Visit;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface VisitFacadeLocal {

    void create(Visit visit);

    void edit(Visit visit);

    void remove(Visit visit);

    Visit find(Object id);

    List<Visit> findAll();

}
