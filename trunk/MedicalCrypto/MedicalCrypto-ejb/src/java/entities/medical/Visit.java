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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "visit")
@NamedQueries({
    @NamedQuery(name = "Visit.findByIdVisit", query = "SELECT v FROM Visit v WHERE v.idVisit = :idVisit"), 
    @NamedQuery(name = "Visit.findByDiagnose", query = "SELECT v FROM Visit v WHERE v.diagnose = :diagnose"), 
    @NamedQuery(name = "Visit.findByInfo", query = "SELECT v FROM Visit v WHERE v.info = :info"), 
    @NamedQuery(name = "Visit.findByDate", query = "SELECT v FROM Visit v WHERE v.date = :date"), 
    @NamedQuery(name = "Visit.findByVersion", query = "SELECT v FROM Visit v WHERE v.version = :version")
})
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_visit", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger idVisit;
    @Column(name = "diagnose", nullable = false)
    private String diagnose;
    @Column(name = "info", nullable = false)
    private String info;
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Lob
    @Column(name = "iv", nullable = false)
    private byte[] iv;
    @Version
    @Column(name = "version")
    private Integer version;
    @OneToMany(mappedBy = "visitId")
    private List<Treatment> treatmentList= new ArrayList<Treatment>();
    @JoinColumn(name = "key_manifest_id", referencedColumnName = "id_key_manifest")
    @ManyToOne
    private KeyManifest keyManifestId;
    @JoinColumn(name = "doctor_id", referencedColumnName = "id_persons", nullable=false)
    @ManyToOne
    private Persons doctorId;
    @JoinColumn(name = "patient_id", referencedColumnName = "id_persons", nullable=false)
    @ManyToOne
    private Persons patientId;

    public Visit() {
    }

    public Visit(String diagnose, String info, Date date, byte[] iv) {
        this.diagnose = diagnose;
        this.info = info;
        this.date = date;
        this.iv = iv;
    }

    public BigInteger getIdVisit() {
        return idVisit;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }

    public KeyManifest getKeyManifestId() {
        return keyManifestId;
    }

    public void setKeyManifestId(KeyManifest keyManifestId) {
        this.keyManifestId = keyManifestId;
    }

    public Persons getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Persons doctorId) {
        this.doctorId = doctorId;
    }

    public Persons getPatientId() {
        return patientId;
    }

    public void setPatientId(Persons patientId) {
        this.patientId = patientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisit != null ? idVisit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visit)) {
            return false;
        }
        Visit other = (Visit) object;
        if ((this.idVisit == null && other.idVisit != null) || (this.idVisit != null && !this.idVisit.equals(other.idVisit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.medical.Visit[idVisit=" + idVisit + "]";
    }
}
