/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Piotrek
 */
public class SecKeyNotFoundException extends Exception{

    public SecKeyNotFoundException() {
        super("SecKey not found");
    }

}
