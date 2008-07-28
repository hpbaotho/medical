/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.stateless;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
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
        AlgorithmParameterSpec initializationVector;
        if ("RC2".equals(seckey.getAlgorithm())) {
            initializationVector = new RC2ParameterSpec(seckey.getEncoded().length, iv);
        } else {
            initializationVector = new IvParameterSpec(iv);
        }
        Cipher cipher = this.initializeCipher(seckey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, seckey, initializationVector);
        return cipher.doFinal(plainText);
    }

    public byte[] decrypt(final byte[] cipherText, final byte[] iv, final SecretKey seckey)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {
        AlgorithmParameterSpec initializationVector;
        if ("RC2".equals(seckey.getAlgorithm())) {
            initializationVector = new RC2ParameterSpec(seckey.getEncoded().length, iv);
        } else {
            initializationVector = new IvParameterSpec(iv);
        }
        Cipher cipher = this.initializeCipher(seckey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, seckey, initializationVector);
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
