/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.stateless;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;

/**
 *
 * @author Piotrek
 */
@Stateless
public class CryptoMachineBean implements CryptoMachineLocal {

    public byte[] encrypt(final byte[] plainText, final byte[] iv, final SecretKey seckey)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {
        String algorithm = seckey.getAlgorithm();
        IvParameterSpec initializationVector = new IvParameterSpec(iv);
        Cipher cipher = this.initializeCipher(algorithm);
        SecretKey key = new SecretKeySpec(seckey.getEncoded(), algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, initializationVector);
        return cipher.doFinal(plainText);
    }

    public byte[] decrypt(final byte[] cipherText, final byte[] iv, final SecretKey seckey)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {
        String algorithm = seckey.getAlgorithm();
        IvParameterSpec initializationVector = new IvParameterSpec(iv);
        Cipher cipher = this.initializeCipher(algorithm);
        SecretKey key = new SecretKeySpec(seckey.getEncoded(), algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, initializationVector);
        return cipher.doFinal(cipherText);
    }

    private Cipher initializeCipher(String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
        if ("Blowfish".equals(algorithm)) {
            return Cipher.getInstance("Blowfish/CBC/PKCS5Padding");
        } else if ("AES".equals(algorithm)) {
            return Cipher.getInstance("AES/CBC/PKCS5Padding");
        } else if ("DES".equals(algorithm)) {
            return Cipher.getInstance("DES/CBC/PKCS5Padding");
        } else if ("DESede".equals(algorithm)) {
            return Cipher.getInstance("DESede/CBC/PKCS5Padding");
        } else if ("RC2".equals(algorithm)) {
            return Cipher.getInstance("RC2/CBC/PKCS5Padding");
        } else {
            throw new NoSuchAlgorithmException();
        }
    }
}
