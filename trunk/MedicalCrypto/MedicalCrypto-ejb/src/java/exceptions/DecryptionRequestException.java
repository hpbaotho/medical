/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Piotrek
 */
public class DecryptionRequestException extends Exception{

    public DecryptionRequestException() {
        super("Decryption request error");
    }

}
