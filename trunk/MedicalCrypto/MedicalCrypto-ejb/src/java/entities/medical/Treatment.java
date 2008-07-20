/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.medical;

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
import javax.persistence.Version;

/**
 *
 * @author Piotrek
 */
@Entity
@Table(name = "treatment")
@NamedQueries({
    @NamedQuery(name = "Treatment.findByIdTreatment", query = "SELECT t FROM Treatment t WHERE t.idTreatment = :idTreatment"), 
    @NamedQuery(name = "Treatment.findByMedicine", query = "SELECT t FROM Treatment t WHERE t.medicine = :medicine"), 
    @NamedQuery(name = "Treatment.findByDosage", query = "SELECT t FROM Treatment t WHERE t.dosage = :dosage"), 
    @NamedQuery(name = "Treatment.findByVersion", query = "SELECT t FROM Treatment t WHERE t.version = :version")
})
public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_treatment", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger idTreatment;
    @Column(name = "medicine", nullable = false)
    private String medicine;
    @Column(name = "dosage", nullable = false)
    private String dosage;
    @Lob
    @Column(name = "iv", nullable = false)
    private byte[] iv;
    @Version
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "key_manifest_id", referencedColumnName = "id_key_manifest", nullable=false)
    @ManyToOne
    private KeyManifest keyManifestId;
    @JoinColumn(name = "visit_id", referencedColumnName = "id_visit", nullable=false)
    @ManyToOne
    private Visit visitId;

    public Treatment() {
    }

    public Treatment(String medicine, String dosage, byte[] iv) {
        this.medicine = medicine;
        this.dosage = dosage;
        this.iv = iv;
    }

    public BigInteger getIdTreatment() {
        return idTreatment;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public KeyManifest getKeyManifestId() {
        return keyManifestId;
    }

    public void setKeyManifestId(KeyManifest keyManifestId) {
        this.keyManifestId = keyManifestId;
    }

    public Visit getVisitId() {
        return visitId;
    }

    public void setVisitId(Visit visitId) {
        this.visitId = visitId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTreatment != null ? idTreatment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Treatment)) {
            return false;
        }
        Treatment other = (Treatment) object;
        if ((this.idTreatment == null && other.idTreatment != null) || (this.idTreatment != null && !this.idTreatment.equals(other.idTreatment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.medical.Treatment[idTreatment=" + idTreatment + "]";
    }
}
