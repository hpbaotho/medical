/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalcryptoappclient.gui.validators;

import java.awt.Color;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Piotrek
 */
public class PassValidator extends InputVerifier {

    JLabel errorLabel;

    public PassValidator(JLabel errorLabel) {
        this.errorLabel = errorLabel;
        this.errorLabel.setVisible(false);
    }

    @Override
    public boolean verify(JComponent input) {
        JTextField textFieldToValidate = (JTextField) input;
        String textToValidate = textFieldToValidate.getText();
        boolean result = false;
        boolean error = true;
        Color color= Color.red;
        if (textToValidate.length() > 5) {
            result = true;
            error = false;
            color= Color.white;
        }
        textFieldToValidate.setBackground(color);
        errorLabel.setVisible(error);
        return result;
    }
}
