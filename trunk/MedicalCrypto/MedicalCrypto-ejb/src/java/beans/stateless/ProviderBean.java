/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.stateless;

import exceptions.SecKeyNotFoundException;
import beans.facades.crypto.SeckeyFacadeLocal;
import beans.facades.medical.KeyManifestFacadeLocal;
import entities.crypto.Seckey;
import entities.medical.KeyManifest;
import exceptions.InvalidSeedException;
import exceptions.DecryptionRequestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.security.RolesAllowed;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author Piotrek
 */
@Stateless
@RolesAllowed(value = {"doctor", "patient", "nurse"})
public class ProviderBean implements ProviderLocal {

    @EJB
    private CryptoMachineLocal cryptoMachineBean;
    @EJB
    private KeyManifestFacadeLocal keyManifestFacade;
    @EJB
    private SeckeyFacadeLocal seckeyFacade;

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public CipherTask encrypt(CipherTask encryptRequest, String family)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidSeedException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException,
            SecKeyNotFoundException {
        KeyManifest liveKeyManifest = this.findLiveKey(family);
        Seckey secKeyWrapped = seckeyFacade.find(liveKeyManifest.getKeyId());
        if (secKeyWrapped != null) {
            SecretKey secKeyTransparent = this.unwrapSecKey(
                    secKeyWrapped.getSeckey(),
                    secKeyWrapped.getEngineId().getEngine(),
                    this.unwrapEkey(secKeyWrapped.getEkeyId().getEkey(), secKeyWrapped.getEkeyId().getActivationDate().getTime()));
            byte[] iv = this.createIV(secKeyTransparent.getAlgorithm());
            HashMap encryptionRequestData = encryptRequest.getData();
            Set columnsToEncrypt = encryptionRequestData.keySet();
            for (Iterator it = columnsToEncrypt.iterator(); it.hasNext();) {
                String column = (String) it.next();
                String plainText = (String) encryptionRequestData.get(column);
                String cipherText = bytes2HexString(cryptoMachineBean.encrypt(plainText.getBytes(), iv, secKeyTransparent));
                encryptionRequestData.put(column, cipherText);
            }
            encryptRequest.setAliasId(liveKeyManifest.getIdKeyManifest());
            encryptRequest.setIv(iv);
            return encryptRequest;
        }
        throw new SecKeyNotFoundException();
    }

    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public CipherTask decrypt(CipherTask decryptionRequest)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidSeedException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException,
            SecKeyNotFoundException,
            DecryptionRequestException {
        if (decryptionRequest.getAliasId() != null && decryptionRequest.getIv() != null) {
            KeyManifest liveKeyManifest = keyManifestFacade.find(decryptionRequest.getAliasId());
            Seckey secKeyWrapped = seckeyFacade.find(liveKeyManifest.getKeyId());
            if (secKeyWrapped != null) {
                SecretKey secKeyTransparent = this.unwrapSecKey(
                        secKeyWrapped.getSeckey(),
                        secKeyWrapped.getEngineId().getEngine(),
                        this.unwrapEkey(secKeyWrapped.getEkeyId().getEkey(), secKeyWrapped.getEkeyId().getActivationDate().getTime()));
                byte[] iv = decryptionRequest.getIv();
                HashMap decryptionRequestData = decryptionRequest.getData();
                Set columnsToDecrypt = decryptionRequestData.keySet();
                for (Iterator it = columnsToDecrypt.iterator(); it.hasNext();) {
                    String column = (String) it.next();
                    String cipherText = (String) decryptionRequestData.get(column);
                    String plainText = new String(cryptoMachineBean.decrypt(hexString2Bytes(cipherText), iv, secKeyTransparent));
                    decryptionRequestData.put(column, plainText);
                }
                return decryptionRequest;
            }
            throw new SecKeyNotFoundException();
        }
        throw new DecryptionRequestException(new Throwable("No keyManifest or IV"));
    }

    private KeyManifest findLiveKey(String family) {
        List<KeyManifest> keyManifestList = keyManifestFacade.findByFamilyStatus(family, "ACTIVE");
        Collections.sort(keyManifestList);
        Date now = Calendar.getInstance().getTime();
        KeyManifest liveKeyManifest = null;
        for (int i = keyManifestList.size() - 1; i >= 0; i--) {
            KeyManifest keyManifest = keyManifestList.get(i);
            keyManifestFacade.refresh(keyManifest);
            if (keyManifest.getKeyActivationDate().after(now)) {
                continue;
            } else {
                liveKeyManifest = keyManifest;
                break;
            }
        }
        return liveKeyManifest;
    }

    private final SecretKey unwrapSecKey(byte[] wrappedKey, String keyAlgorithm, SecretKey kek) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Cipher unwrapper = Cipher.getInstance("AESWrap");
        unwrapper.init(Cipher.UNWRAP_MODE, kek);
        SecretKey unwrapppedKey = (SecretKey) unwrapper.unwrap(wrappedKey, keyAlgorithm, Cipher.SECRET_KEY);
        return unwrapppedKey;
    }

    private final SecretKey unwrapEkey(byte[] wrappedKey, long seed) throws InvalidSeedException {
        byte[] wrapper = createSeed(seed);
        byte[] key = new byte[16];
        for (int i = 0; i < key.length; i++) {
            key[i] = (byte) (wrappedKey[i] ^ wrapper[i]);
        }
        byte[] keyCheck = new byte[16];
        for (int i = 0; i < keyCheck.length; i++) {
            keyCheck[i] = (byte) (key[i] ^ wrapper[i]);
            if (keyCheck[i] == wrappedKey[i]) {
                continue;
            }
            throw new InvalidSeedException();
        }
        SecretKeySpec kek = new SecretKeySpec(key, "AES");
        return kek;
    }

    private final byte[] createSeed(long number) {
        byte[] numberInBytes = Long.toString(number).getBytes();
        byte[] seed = new byte[16];
        for (int i = 0, j = 0; i < seed.length; i++, j++) {
            try {
                seed[i] = numberInBytes[j];
            } catch (ArrayIndexOutOfBoundsException ex) {
                j = 0;
                seed[i] = numberInBytes[j];
            }
        }
        return seed;
    }

    private final byte[] createIV(String algorithm) throws NoSuchAlgorithmException {
        SecureRandom ivGenerator = SecureRandom.getInstance("SHA1PRNG");
        int size;
        if ("Blowfish".equals(algorithm) || "DES".equals(algorithm) || "DESede".equals(algorithm) || "RC2".equals(algorithm)) {
            size = 8;
        } else if ("AES".equals(algorithm)) {
            size = 16;
        } else {
            throw new NoSuchAlgorithmException();
        }
        byte[] iv = new byte[size];
        ivGenerator.nextBytes(iv);
        return iv;
    }

    private String bytes2HexString(byte[] bytes) {
        String result = "";
        String convertedByte;
        for (int i = 0; i < bytes.length; i++) {
            convertedByte = Integer.toHexString(bytes[i]);
            if (convertedByte.length() < 2) {
                convertedByte = "00".substring(convertedByte.length()) + convertedByte;
            } else if (convertedByte.length() > 2) {
                convertedByte = convertedByte.substring(convertedByte.length() - 2);
            }
            result += convertedByte.toUpperCase();
        }
        return result;
    }

    private byte[] hexString2Bytes(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        byte leftHalf = 0x0;
        byte rightHalf = 0x0;
        for (int i = 0, j = 0; i < hexString.length() / 2; i++, j = i * 2) {
            rightHalf = (byte) (Byte.parseByte(hexString.substring(j + 1, j + 2), 16) & (byte) 0xF);
            leftHalf = (byte) ((Byte.parseByte(hexString.substring(j, j + 1), 16) << 4) & (byte) 0xF0);
            bytes[i] = (byte) (leftHalf | rightHalf);
        }
        return bytes;
    }
}
