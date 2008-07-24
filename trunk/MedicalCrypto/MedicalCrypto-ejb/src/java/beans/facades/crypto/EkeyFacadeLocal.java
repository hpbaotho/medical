/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.crypto;

import entities.crypto.Ekey;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface EkeyFacadeLocal {

    void create(Ekey ekey);

    void edit(Ekey ekey);

    void remove(Ekey ekey);

    Ekey find(Object id);

    List<Ekey> findAll();

}