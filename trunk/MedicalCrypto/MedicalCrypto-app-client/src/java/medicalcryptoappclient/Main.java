/*
 * Main.java
 *
 * Created on 22 marzec 2007, 22:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package medicalcryptoappclient;



/**
 *
 * @author Piotrek
 */
public class Main {
    
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoggingGUI oknologowania= new LoggingGUI();
        oknologowania.pack();
        oknologowania.setLocationRelativeTo(null);
        oknologowania.setVisible(true);
        
        
        //okno.label().setText(""+okno.getSize().getHeight());
    }
    
    
}
