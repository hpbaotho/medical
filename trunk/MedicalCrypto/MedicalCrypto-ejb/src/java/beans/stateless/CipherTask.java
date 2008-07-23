/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package beans.stateless;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;

/**
 *
 * @author Piotrek
 */
public class CipherTask implements Serializable{
    private HashMap<String, String> data;
    private byte[] iv;
    private BigInteger aliasId;
    
    public CipherTask(){
        
    }
    
    public CipherTask(HashMap<String, String> data, byte[] iv, BigInteger aliasId){
        this.data= data;
        this.iv= iv;
        this.aliasId= aliasId;
    }
    
    public CipherTask(HashMap<String, String> data){
        this.data= data;
        this.iv= null;
        this.aliasId= null;
    }

    public HashMap getData() {
        return data;
    }

    public void setData(HashMap data) {
        this.data = data;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public BigInteger getAliasId() {
        return aliasId;
    }

    public void setAliasId(BigInteger aliadId) {
        this.aliasId = aliadId;
    }
    
    

}
