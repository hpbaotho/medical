/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.crypto;

import entities.crypto.Seckey;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface SeckeyFacadeLocal {

    void create(Seckey seckey);

    void edit(Seckey seckey);

    void remove(Seckey seckey);

    Seckey find(Object id);

    List<Seckey> findAll();

}
