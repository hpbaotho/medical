/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.facades.medical;

import entities.medical.KeyManifest;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface KeyManifestFacadeLocal {

    void create(KeyManifest keyManifest);

    void edit(KeyManifest keyManifest);

    void remove(KeyManifest keyManifest);

    KeyManifest find(Object id);

    List<KeyManifest> findAll();

}
