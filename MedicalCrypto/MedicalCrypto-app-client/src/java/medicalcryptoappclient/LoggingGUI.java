/*
 * Logowanie.java
 *
 * Created on 24 marzec 2007, 01:05
 */
package medicalcryptoappclient;

import beans.statefull.DoctorRemote;
import com.sun.appserv.security.ProgrammaticLogin;
import javax.naming.InitialContext;
import beans.statefull.LoggingRemote;
import beans.statefull.NurseRemote;
import beans.statefull.PatientRemote;
import beans.statefull.SearchRemote;
import entities.medical.dto.PersonsDTO;
import medicalcryptoappclient.gui.GUIDoctor;
import medicalcryptoappclient.gui.GUINurse;
import medicalcryptoappclient.gui.GUIPatient;

/**
 *
 * @author  Piotrek
 */
public class LoggingGUI extends javax.swing.JFrame {

    /** Creates new form Logowanie */
    public LoggingGUI() {
        initComponents();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        AloginTF = new javax.swing.JTextField();
        ApasswordPF = new javax.swing.JPasswordField();
        zalogujB = new javax.swing.JButton();
        AbladL = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Logowanie");
        setResizable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Logowanie"));

        jLabel7.setText("Login:");

        jLabel8.setText("Hasło:");

        zalogujB.setText("Zaloguj");
        zalogujB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zalogujBActionPerformed(evt);
            }
        });

        AbladL.setForeground(new java.awt.Color(255, 0, 0));

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel8)
                    .add(jLabel7))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(AloginTF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .add(ApasswordPF, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
            .add(zalogujB, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
            .add(AbladL, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(AloginTF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ApasswordPF, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(AbladL, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(zalogujB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void zalogujBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zalogujBActionPerformed
// TODO add your handling code here:

        AbladL.setText("");
        ProgrammaticLogin programmaticLogin = new ProgrammaticLogin();
        try {
            String pass = new String(ApasswordPF.getPassword());
            char[] login = AloginTF.getText().toCharArray();
            char[] flippedLogin = new char[login.length];
            for (int i = login.length - 1, j = 0; i >= 0; i--, j++) {
                flippedLogin[j] = login[i];
            }
            String user = new String(flippedLogin);
            programmaticLogin.login(user, pass);
            InitialContext ic = new InitialContext();
            LoggingRemote loggingBean = (LoggingRemote) ic.lookup("ejb/LoggingBean");
            PersonsDTO loggedUser = loggingBean.getLoggedUser();
            String role = loggedUser.getRole();
            if ("doctor".equals(role)) {
                System.out.println("Doctor");
                DoctorRemote doctorBean = (DoctorRemote) ic.lookup("ejb/DoctorBean");
                SearchRemote searchBean = (SearchRemote) ic.lookup("ejb/SearchBean");
                this.setVisible(false);
                GUIDoctor mainWindow = new GUIDoctor(loggedUser, doctorBean, searchBean);
                mainWindow.setLocationRelativeTo(this);
                mainWindow.setVisible(true);
            } else if ("patient".equals(role)) {
                System.out.println("Patient");
                PatientRemote patientBean = (PatientRemote) ic.lookup("ejb/PatientBean");
                this.setVisible(false);
                GUIPatient mainWindow = new GUIPatient(loggedUser, patientBean);
                mainWindow.setLocationRelativeTo(this);
                mainWindow.setVisible(true);
            } else if ("nurse".equals(role)) {
                System.out.println("Nurse");
                NurseRemote nurseBean = (NurseRemote) ic.lookup("ejb/NurseBean");
                SearchRemote searchBean = (SearchRemote) ic.lookup("ejb/SearchBean");
                this.setVisible(false);
                GUINurse mainWindow = new GUINurse(loggedUser, nurseBean, searchBean);
                mainWindow.setLocationRelativeTo(this);
                mainWindow.setVisible(true);
            } else {
                throw new Exception();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            AbladL.setText("Błąd logowania.");
        }

    }//GEN-LAST:event_zalogujBActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AbladL;
    private javax.swing.JTextField AloginTF;
    private javax.swing.JPasswordField ApasswordPF;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton zalogujB;
    // End of variables declaration//GEN-END:variables
}
