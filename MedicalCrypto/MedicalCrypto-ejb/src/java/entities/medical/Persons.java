/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.medical;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Version;

/**
 *
 * @author Piotrek
 */
@Entity
@Table(name = "persons")
@NamedQueries({
    @NamedQuery(name = "Persons.findByIdPersons", query = "SELECT p FROM Persons p WHERE p.idPersons = :idPersons"), 
    @NamedQuery(name = "Persons.findByLogin", query = "SELECT p FROM Persons p WHERE p.login = :login"), 
    @NamedQuery(name = "Persons.findByPass", query = "SELECT p FROM Persons p WHERE p.pass = :pass"), 
    @NamedQuery(name = "Persons.findByName", query = "SELECT p FROM Persons p WHERE p.name = :name"), 
    @NamedQuery(name = "Persons.findByInn", query = "SELECT p FROM Persons p WHERE p.inn = :inn"), 
    @NamedQuery(name = "Persons.findBySurname", query = "SELECT p FROM Persons p WHERE p.surname = :surname"), 
    @NamedQuery(name = "Persons.findByIns", query = "SELECT p FROM Persons p WHERE p.ins = :ins"), 
    @NamedQuery(name = "Persons.findByStreet", query = "SELECT p FROM Persons p WHERE p.street = :street"), 
    @NamedQuery(name = "Persons.findByNumber", query = "SELECT p FROM Persons p WHERE p.number = :number"), 
    @NamedQuery(name = "Persons.findByCity", query = "SELECT p FROM Persons p WHERE p.city = :city"), 
    @NamedQuery(name = "Persons.findByZip", query = "SELECT p FROM Persons p WHERE p.zip = :zip"), 
    @NamedQuery(name = "Persons.findByPhone", query = "SELECT p FROM Persons p WHERE p.phone = :phone"), 
    @NamedQuery(name = "Persons.findByPesel", query = "SELECT p FROM Persons p WHERE p.pesel = :pesel"), 
    @NamedQuery(name = "Persons.findByRol", query = "SELECT p FROM Persons p WHERE p.role = :role"), 
    @NamedQuery(name = "Persons.findByVersion", query = "SELECT p FROM Persons p WHERE p.version = :version")
})
public class Persons implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_persons", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger idPersons;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "pass", nullable = false)
    private String pass;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "inn", nullable = false)
    private char inn;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "ins", nullable = false)
    private char ins;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "number", nullable = false)
    private int number;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "zip", nullable = false)
    private int zip;
    @Column(name = "phone", nullable = false)
    private int phone;
    @Column(name = "pesel", nullable = false)
    private long pesel;
    @Column(name = "role", nullable = false)
    private String role;
    @Lob
    @Column(name = "iv", nullable = false)
    private byte[] iv;
    @Version
    @Column(name = "version")
    private Integer version;
    @OneToMany(mappedBy = "doctorId")
    private List<Visit> visitDocList= new ArrayList<Visit>();
    @OneToMany(mappedBy = "patientId")
    private List<Visit> visitPatientList= new ArrayList<Visit>();
    @JoinColumn(name = "key_manifest_id", referencedColumnName = "id_key_manifest", nullable=false)
    @ManyToOne
    private KeyManifest keyManifestId;

    public Persons() {
    }

    public Persons(String login, String pass, String name, String surname, String street, int number, 
            String city, int zip, int phone, long pesel, String role, byte[] iv) {
        this.login = login;
        this.pass = pass;
        this.name = name;
        this.inn = name.charAt(0);
        this.surname = surname;
        this.ins = surname.charAt(0);
        this.street = street;
        this.number = number;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.pesel = pesel;
        this.role = role;
        this.iv = iv;
    }

    public BigInteger getIdPersons() {
        return idPersons;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getInn() {
        return inn;
    }

    public void setInn(char inn) {
        this.inn = inn;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public char getIns() {
        return ins;
    }

    public void setIns(char ins) {
        this.ins = ins;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public List<Visit> getVisitDocList() {
        return visitDocList;
    }

    public void setVisitDocList(List<Visit> visitDocList) {
        this.visitDocList = visitDocList;
    }

    public List<Visit> getVisitPatientList() {
        return visitPatientList;
    }

    public void setVisitPatientList(List<Visit> visitPatientList) {
        this.visitPatientList = visitPatientList;
    }

    public KeyManifest getKeyManifestId() {
        return keyManifestId;
    }

    public void setKeyManifestId(KeyManifest keyManifestId) {
        this.keyManifestId = keyManifestId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersons != null ? idPersons.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persons)) {
            return false;
        }
        Persons other = (Persons) object;
        if ((this.idPersons == null && other.idPersons != null) || (this.idPersons != null && !this.idPersons.equals(other.idPersons))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.medical.Persons[idPersons=" + idPersons + "]";
    }
}
