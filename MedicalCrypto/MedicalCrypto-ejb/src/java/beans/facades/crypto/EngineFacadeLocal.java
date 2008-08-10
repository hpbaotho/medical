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

    void create(Engine engine);

    void edit(Engine engine);

    void remove(Engine engine);

    Engine find(Object id);

    List<Engine> findAll();
    
    void refresh(Engine toRefresh);

}
