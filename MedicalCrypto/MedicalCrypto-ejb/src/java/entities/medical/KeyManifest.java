/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.medical;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author Piotrek
 */
@Entity
@Table(name = "key_manifest")
@NamedQueries({
    @NamedQuery(name = "KeyManifest.findByIdKeyManifest", query = "SELECT k FROM KeyManifest k WHERE k.idKeyManifest = :idKeyManifest"), 
    @NamedQuery(name = "KeyManifest.findByKeyAlias", query = "SELECT k FROM KeyManifest k WHERE k.keyAlias = :keyAlias"), 
    @NamedQuery(name = "KeyManifest.findByKeyFamily", query = "SELECT k FROM KeyManifest k WHERE k.keyFamily = :keyFamily"), 
    @NamedQuery(name = "KeyManifest.findByKeyId", query = "SELECT k FROM KeyManifest k WHERE k.keyId = :keyId"), 
    @NamedQuery(name = "KeyManifest.findByKeyActivationDate", query = "SELECT k FROM KeyManifest k WHERE k.keyActivationDate = :keyActivationDate"), 
    @NamedQuery(name = "KeyManifest.findByStatus", query = "SELECT k FROM KeyManifest k WHERE k.status = :status"), 
    @NamedQuery(name = "KeyManifest.findByKeyFamilyStatus", query = "SELECT k FROM KeyManifest k WHERE k.keyFamily = :keyFamily AND k.status = :status")
})
public class KeyManifest implements Serializable, Comparable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_key_manifest", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger idKeyManifest;
    @Column(name = "key_alias", nullable = false)
    private String keyAlias;
    @Column(name = "key_family", nullable = false)
    private String keyFamily;
    @Column(name = "key_id", nullable = false)
    private long keyId;
    @Column(name = "key_activation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date keyActivationDate;
    @Column(name = "status", nullable = false)
    private String status;
    @Version
    @Column(name = "version")
    private Integer version;
    @OneToMany(mappedBy = "keyManifestId")
    private List<Treatment> treatmentList= new ArrayList<Treatment>();
    @OneToMany(mappedBy = "keyManifestId")
    private List<Visit> visitList= new ArrayList<Visit>();
    @OneToMany(mappedBy = "keyManifestId")
    private List<Persons> personsList= new ArrayList<Persons>();

    public KeyManifest() {
    }

    public KeyManifest(String keyAlias, String keyFamily, long keyId, Date keyActivationDate, String status) {
        this.keyAlias = keyAlias;
        this.keyFamily = keyFamily;
        this.keyId = keyId;
        this.keyActivationDate = keyActivationDate;
        this.status = status;
    }

    public BigInteger getIdKeyManifest() {
        return idKeyManifest;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyFamily() {
        return keyFamily;
    }

    public void setKeyFamily(String keyFamily) {
        this.keyFamily = keyFamily;
    }

    public long getKeyId() {
        return keyId;
    }

    public void setKeyId(long keyId) {
        this.keyId = keyId;
    }

    public Date getKeyActivationDate() {
        return keyActivationDate;
    }

    public void setKeyActivationDate(Date keyActivationDate) {
        this.keyActivationDate = keyActivationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }

    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    public List<Persons> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<Persons> personsList) {
        this.personsList = personsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idKeyManifest != null ? idKeyManifest.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KeyManifest)) {
            return false;
        }
        KeyManifest other = (KeyManifest) object;
        if ((this.idKeyManifest == null && other.idKeyManifest != null) || (this.idKeyManifest != null && !this.idKeyManifest.equals(other.idKeyManifest))) {
            return false;
        }
        return true;
    }
    
    public int compareTo(Object o) {
        KeyManifest compare= (KeyManifest)o;
        if(keyActivationDate.before(compare.getKeyActivationDate()))
            return -1;
        else if(keyActivationDate.after(compare.getKeyActivationDate()))
            return 1;
        else
            return 0;
        
    }

    @Override
    public String toString() {
        return "entities.medical.KeyManifest[idKeyManifest=" + idKeyManifest + "]";
    }
}
