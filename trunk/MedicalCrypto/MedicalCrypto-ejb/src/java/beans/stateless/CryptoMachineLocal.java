/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.stateless;

import entities.crypto.Seckey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.Local;

/**
 *
 * @author Piotrek
 */
@Local
public interface CryptoMachineLocal {

    public byte[] encrypt(final byte[] plainText, final byte[] iv, Seckey seckey)
            throws NoSuchAlgorithmException, 
            NoSuchPaddingException, 
            InvalidKeyException, 
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException;

    public byte[] decrypt(final byte[] plainText, final byte[] iv, Seckey seckey)
            throws NoSuchAlgorithmException, 
            NoSuchPaddingException, 
            InvalidKeyException, 
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException;
}
