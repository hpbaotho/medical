/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Piotrek
 */
public class CryptographyException extends Exception{
    
    public CryptographyException(Throwable reason){
        super("Cryptography exception",reason);
    }

}
