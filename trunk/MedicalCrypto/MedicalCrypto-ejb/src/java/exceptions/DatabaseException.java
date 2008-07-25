/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exceptions;

/**
 *
 * @author Piotrek
 */
public class DatabaseException extends Exception{
    
    public DatabaseException(Throwable reason){
        super("Database exception",reason);
    }

}
