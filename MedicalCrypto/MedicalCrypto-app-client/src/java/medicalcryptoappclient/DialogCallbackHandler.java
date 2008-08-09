package medicalcryptoappclient;

import java.awt.GridLayout;
import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DialogCallbackHandler implements CallbackHandler {

    private JTextField loginField = null;
    private JPasswordField passwordField = null;
    private boolean cancelled = false;
    private String title = "Logowanie";
    private String username = "Użytkowik ";
    private String password = "Hasło ";
    private String loginButton = "Zaloguj";
    private String cancelButton = "Anuluj";
    private static final int MAX_FIELD_LENGTH = 20;
    private int passwordLength = MAX_FIELD_LENGTH;
    private int usernameLength = MAX_FIELD_LENGTH;
    private char echoChar = '*';
    private boolean echoCharOn = false;
    private String[] connectOptionNames;

    public DialogCallbackHandler() {
        super();
        int i = 0;
        connectOptionNames = new String[2];
        connectOptionNames[i] = loginButton;
        connectOptionNames[++i] = cancelButton;
    }

    public DialogCallbackHandler(String title) {
        this();
        this.title = title;
    }

    public DialogCallbackHandler(String title, String username, String password) {
        this();
        this.title = title;
        this.username = username;
        this.password = password;

    }

    public DialogCallbackHandler(String title, String username, String password, String loginButton, String cancelButton, int usernameLength, int passwordLength, char echoChar) {
        this(title, username, password);
        this.echoCharOn = true;
        this.echoChar = echoChar;
        this.loginButton = loginButton;
        this.cancelButton = cancelButton;
        this.passwordLength = passwordLength;
        this.usernameLength = usernameLength;
        this.echoChar = echoChar;
    }

    private void dialogInit(boolean isEchoOn) {
        echoCharOn = (isEchoOn || echoCharOn);
        dialogInit();
    }

    private void dialogInit() {
        JLabel userNameLabel = new JLabel(username, JLabel.RIGHT);
        loginField = new JTextField("");
        JLabel passwordLabel = new JLabel(password, JLabel.RIGHT);
        if (!echoCharOn) {
            passwordField = new JPasswordField(passwordLength);
            ((JPasswordField) passwordField).setEchoChar(echoChar);
        } else {
            passwordField = new JPasswordField(passwordLength);
        }
        JPanel connectionPanel = new JPanel(false);
        connectionPanel.setLayout(new BoxLayout(connectionPanel, BoxLayout.X_AXIS));
        JPanel namePanel = new JPanel(false);
        namePanel.setLayout(new GridLayout(0, 1));
        namePanel.add(userNameLabel);
        namePanel.add(passwordLabel);
        JPanel fieldPanel = new JPanel(false);
        fieldPanel.setLayout(new GridLayout(0, 1));
        fieldPanel.add(loginField);
        fieldPanel.add(passwordField);
        connectionPanel.add(namePanel);
        connectionPanel.add(fieldPanel);
        int choice = JOptionPane.showOptionDialog(null, connectionPanel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, connectOptionNames, loginField);
        if (choice == JOptionPane.OK_OPTION) {
            cancelled = false;
        } else if (choice == JOptionPane.CANCEL_OPTION) {
            cancelled = true;
        } else if (choice == JOptionPane.CLOSED_OPTION) {
            cancelled = true;
        } else {
            cancelled = true;
        }
        if (cancelled) {
            loginField.setText("Invalid");
            passwordField.setText("Invalid");
        }
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        if (cancelled) {
            return;
        }
        int i = 0;
        boolean found = false;
        while ((i < callbacks.length) && !found) {
            if (callbacks[i] instanceof PasswordCallback) {
                found = true;
                dialogInit(((PasswordCallback) callbacks[i]).isEchoOn());
            }
            i++;
        }
        if (!found) {
            dialogInit();
        }
        for (i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                ((NameCallback) callbacks[i]).setName(loginField.getText());
            } else if (callbacks[i] instanceof PasswordCallback) {
                ((PasswordCallback) callbacks[i]).setPassword((passwordField.getPassword()));
            } else {
                throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
            }
        }
    }
}