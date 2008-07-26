/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.medical.dto;

/**
 *
 * @author Piotrek
 */
public class Dict {

    public static final String PERSONS= "osoba";
    public static final String VISIT= "wizyta";
    public static final String TREATMENT= "recepta";
    public static final String NAMECOLUMN= "name";
    public static final String SURNAMECOLUMN= "surname";
    public static final String STREETCOLUMN= "street";
    public static final String CITYCOLUMN= "city";
    public static final String PHONECOLUMN= "phone";
    public static final String DIAGNOSECOLUMN= "diagnose";
    public static final String INFOCOLUMN= "info";
    public static final String MEDICINECOLUMN= "medicine";
    public static final String DOSAGECOLUMN= "dosage";

    public static String bytes2HexString(byte[] bytes) {
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
}
