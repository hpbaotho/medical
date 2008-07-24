/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Piotrek
 */
public class PersonsLoginException extends Exception{
    
    public PersonsLoginException(){
        super("Person with this login already exists exception");
    }

}
