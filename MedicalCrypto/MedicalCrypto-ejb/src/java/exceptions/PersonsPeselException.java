/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Piotrek
 */
public class PersonsPeselException extends Exception{
    
    public PersonsPeselException(){
        super("Person already exists exception");
    }

}
