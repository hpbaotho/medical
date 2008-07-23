/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.stateless;

import exceptions.DecryptionRequestException;
import exceptions.InvalidSeedException;
import exceptions.SecKeyNotFoundException;
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
public interface ProviderBeanLocal {

    CipherTask encrypt(CipherTask encryptRequesr, String family) 
            throws NoSuchAlgorithmException, 
            NoSuchPaddingException, 
            InvalidKeyException,
            InvalidSeedException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException,
            SecKeyNotFoundException;
    
    CipherTask decrypt(CipherTask encryptRequest)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidSeedException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException,
            SecKeyNotFoundException,
            DecryptionRequestException;
    
}
