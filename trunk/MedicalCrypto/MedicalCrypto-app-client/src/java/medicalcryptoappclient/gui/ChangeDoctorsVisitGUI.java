/*
 * ChangeDoctorsVisitGUI.java
 *
 * Created on 12 sierpień 2008, 21:28
 */
package medicalcryptoappclient.gui;

import beans.statefull.NurseRemote;
import entities.medical.dto.DoctorDTO;
import entities.medical.dto.PersonsDTO;
import entities.medical.dto.VisitDTO;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author  Piotrek
 */
public class ChangeDoctorsVisitGUI extends JFrame {

    /** Creates new form ChangeDoctorsVisitGUI */
    public ChangeDoctorsVisitGUI(JFrame parent, NurseRemote nurseBean, List<DoctorDTO> doctorsDTOList, PersonsDTO doctorToChangeDTO) {
        initComponents();
        this.parent = parent;
        this.nurseBean = nurseBean;
        for (int i = 0; i < doctorsDTOList.size(); i++) {
            DoctorDTO doctorDTO = doctorsDTOList.get(i);
            doctorsjComboBox.addItem(doctorDTO);
        }
        this.doctorToChangeDTO = doctorToChangeDTO;
        doctorjLabel.setText("Aktualny lekarz: " + doctorToChangeDTO.toString());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        doctorjLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        doctorsjComboBox = new javax.swing.JComboBox();
        OKjButton = new javax.swing.JButton();
        canceljButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Zmień lekarza dla wizyt");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Wybierz lekarza:"));

        doctorjLabel.setText("Aktualny lekarz:");

        jLabel2.setText("Wybierz lekarza:");

        OKjButton.setText("OK");
        OKjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKjButtonActionPerformed(evt);
            }
        });

        canceljButton.setText("Anuluj");
        canceljButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canceljButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(doctorsjComboBox, 0, 179, Short.MAX_VALUE))
                    .addComponent(doctorjLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(canceljButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OKjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(doctorjLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(doctorsjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OKjButton)
                    .addComponent(canceljButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void OKjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKjButtonActionPerformed
    int n = JOptionPane.showConfirmDialog(
            this,
            "Na pewno chcesz zmienić lekarza?",
            "Potwierdź zmianę lekarza",
            JOptionPane.OK_CANCEL_OPTION);
    try {
        if (n == JOptionPane.OK_OPTION) {
            DoctorDTO newDoctorDTO = (DoctorDTO) doctorsjComboBox.getSelectedItem();
            List<VisitDTO> visitsToChangeDoctorDTOList = nurseBean.findVisitByDoctor(doctorToChangeDTO.getIdPersons());
            for (int i = 0; i < visitsToChangeDoctorDTOList.size(); i++) {
                VisitDTO visitDTO = visitsToChangeDoctorDTOList.get(i);
                nurseBean.editVisitDoctor(visitDTO, newDoctorDTO.getIdPersons());
            }
            JOptionPane.showMessageDialog(this,
                    "Pomyślnie zmieniono wszystkie wizyty",
                    "Zmiana lekarzy",
                    JOptionPane.INFORMATION_MESSAGE);
            if (parent instanceof GUINurse) {
                GUINurse parentGUI = (GUINurse) parent;
                parentGUI.callUserItemActionPerformed(evt);
            }
            this.setVisible(false);
            parent.setEnabled(true);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                "Wystąpił błąd przy przetwarzaniu danych\nSkontaktuj się z administratorem",
                "Błąd",
                JOptionPane.ERROR_MESSAGE);
    }

}//GEN-LAST:event_OKjButtonActionPerformed

private void canceljButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canceljButtonActionPerformed
    this.setVisible(false);
    parent.setEnabled(true);
}//GEN-LAST:event_canceljButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton OKjButton;
    private javax.swing.JButton canceljButton;
    private javax.swing.JLabel doctorjLabel;
    private javax.swing.JComboBox doctorsjComboBox;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    JFrame parent;
    NurseRemote nurseBean;
    List<DoctorDTO> doctorsDTOList;
    PersonsDTO doctorToChangeDTO;
}
