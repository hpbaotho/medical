/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.crypto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Piotrek
 */
@Entity
@Table(name = "ekey")
@NamedQueries({
    @NamedQuery(name = "Ekey.findByIdEkey", query = "SELECT e FROM Ekey e WHERE e.idEkey = :idEkey"), 
    @NamedQuery(name = "Ekey.findByActivationDate", query = "SELECT e FROM Ekey e WHERE e.activationDate = :activationDate")
})
public class Ekey implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_ekey", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger idEkey;
    @Lob
    @Column(name = "data", nullable = false)
    private byte[] ekey;
    @Column(name = "activation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date activationDate;
    @OneToMany(mappedBy = "ekeyId")
    private List<Seckey> seckeyList= new ArrayList<Seckey>();

    public Ekey() {
    }

    public Ekey(byte[] ekey, Date activationDate) {
        this.ekey = ekey;
        this.activationDate = activationDate;
    }

    public BigInteger getIdEkey() {
        return idEkey;
    }

    public byte[] getEkey() {
        return ekey;
    }

    public void setEkey(byte[] ekey) {
        this.ekey = ekey;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public List<Seckey> getSeckeyList() {
        return seckeyList;
    }

    public void setSeckeyList(List<Seckey> seckeyList) {
        this.seckeyList = seckeyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEkey != null ? idEkey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ekey)) {
            return false;
        }
        Ekey other = (Ekey) object;
        if ((this.idEkey == null && other.idEkey != null) || (this.idEkey != null && !this.idEkey.equals(other.idEkey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.medical.Ekey[idEkey=" + idEkey + "]";
    }

}
