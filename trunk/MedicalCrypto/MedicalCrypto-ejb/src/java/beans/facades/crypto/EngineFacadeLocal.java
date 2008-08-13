/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.crypto;

import entities.crypto.Engine;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface EngineFacadeLocal {

    Engine find(Object id);

    List<Engine> findAll();
    
    void refresh(Engine toRefresh);

}
