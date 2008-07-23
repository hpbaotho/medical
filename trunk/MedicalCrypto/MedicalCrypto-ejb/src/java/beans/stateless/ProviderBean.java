/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.stateless;

import javax.ejb.Stateless;

/**
 *
 * @author Piotrek
 */
@Stateless
public class ProviderBean implements ProviderLocal {

    public String encrypt(final String plainText, final String family) {
        return null;
    }
    
    
 
}
