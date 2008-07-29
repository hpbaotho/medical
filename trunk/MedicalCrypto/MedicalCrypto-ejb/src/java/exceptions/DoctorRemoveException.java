/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Piotrek
 */
public class DoctorRemoveException extends Exception{
    
    public DoctorRemoveException(){
        super("Doctor has activ visits exception");
    }

}
