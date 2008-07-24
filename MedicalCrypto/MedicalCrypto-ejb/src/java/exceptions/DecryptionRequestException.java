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
public class DecryptionRequestException extends GeneralSecurityException{

    public DecryptionRequestException() {
        super("Decryption request exception");
    }

}
