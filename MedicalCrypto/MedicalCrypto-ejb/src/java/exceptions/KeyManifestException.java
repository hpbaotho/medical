/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.security.GeneralSecurityException;

/**
 *
 * @author Piotrek
 */
public class KeyManifestException extends GeneralSecurityException {

    public KeyManifestException() {
        super("Key manifest id does not match exception");
    }
}
