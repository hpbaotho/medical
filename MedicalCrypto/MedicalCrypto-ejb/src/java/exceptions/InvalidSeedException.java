/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Piotrek
 */
public class InvalidSeedException extends Exception{

    public InvalidSeedException() {
        super("Wrong seed");
    }

}
