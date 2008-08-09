/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalcryptoappclient.gui.validators;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Piotrek
 */
public class NumberValidator extends InputVerifier {

    JLabel errorLabel;
    Pattern pattern = Pattern.compile("[0-9]{1,4}([/][0-9]{1,4})?");

    public NumberValidator(JLabel errorLabel) {
        this.errorLabel = errorLabel;
        this.errorLabel.setVisible(false);
    }

    @Override
    public boolean verify(JComponent input) {
        JTextField textFieldToValidate = (JTextField) input;
        String textToValidate = textFieldToValidate.getText();
        Matcher m = pattern.matcher(textToValidate);
        boolean result = m.matches();
        boolean error;
        Color color;
        if (result) {
            color=Color.white;
            error=false;
        } else {
            color=Color.red;
            error=true;
        }
        textFieldToValidate.setBackground(color);
        errorLabel.setVisible(error);
        return result;
    }
}
