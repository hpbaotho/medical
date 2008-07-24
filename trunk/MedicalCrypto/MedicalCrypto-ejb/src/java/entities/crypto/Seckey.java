/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.crypto;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Piotrek
 */
@Entity
@Table(name = "seckey")
@NamedQueries({
    @NamedQuery(name = "Seckey.findByIdKey", query = "SELECT s FROM Seckey s WHERE s.idKey = :idKey")
})
public class Seckey implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_key", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger idKey;
    @Lob
    @Column(name = "key", nullable = false)
    private byte[] seckey;
    @JoinColumn(name = "ekey_id", referencedColumnName = "id_ekey", nullable=false)
    @ManyToOne
    private Ekey ekeyId;
    @JoinColumn(name = "engine_id", referencedColumnName = "id_engine", nullable=false)
    @ManyToOne
    private Engine engineId;

    public Seckey() {
    }

    public Seckey(byte[] key) {
        this.seckey = key;
    }

    public BigInteger getIdKey() {
        return idKey;
    }

    public byte[] getSeckey() {
        return seckey;
    }

    public Ekey getEkeyId() {
        return ekeyId;
    }

    public Engine getEngineId() {
        return engineId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKey != null ? idKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seckey)) {
            return false;
        }
        Seckey other = (Seckey) object;
        if ((this.idKey == null && other.idKey != null) || (this.idKey != null && !this.idKey.equals(other.idKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.medical.Seckey[idKey=" + idKey + "]";
    }
}
