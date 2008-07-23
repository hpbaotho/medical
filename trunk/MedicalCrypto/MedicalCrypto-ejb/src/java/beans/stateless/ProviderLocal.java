/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.stateless;

import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface ProviderLocal {

    String encrypt(final String plainText, final String family);
    
}
