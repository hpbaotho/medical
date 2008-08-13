/*
 * Haslo.java
 *
 * Created on 2 stycze� 2007, 14:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package medicalcryptoappclient;

import java.io.Serializable;
import java.security.MessageDigest;

/**
 *
 * @author Piotrek
 */
public class PasswordDigest implements Serializable {

    private static final char[] hex = "0123456789abcdef".toCharArray();

    /** Creates a new instance of Haslo */
    public PasswordDigest() {
    }

    /**
     * Returns a message digest of the specified string using the
     * specified digest algorithm.<p>
     *
     * @param cleartext The cleartext string to be digested.
     * @param algorithm The digest algorithm to use (try
     *        "<code>MD5</code>" or "<code>SHA-1</code>").
     *
     * @return A String of hex characters representing the message
     *         digest of the given cleartext string.
     */
    public static String digest(String cleartext) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(cleartext.getBytes("UTF-8")); // Might want to use a
            // specific char encoding?

            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer(2 * digest.length);
            for (int i = 0; i < digest.length; ++i) {
                int high = (digest[i] & 0xf0) >> 4;
                int low = (digest[i] & 0x0f);
                sb.append(hex[high]);
                sb.append(hex[low]);
            }
            return (sb.toString());
        } catch (Exception ex) {
            return null;
        }

    }
}
