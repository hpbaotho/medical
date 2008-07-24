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
public class InvalidSeedException extends GeneralSecurityException{
    public InvalidSeedException() {
        super("Wrong seed exception");
    }

}
