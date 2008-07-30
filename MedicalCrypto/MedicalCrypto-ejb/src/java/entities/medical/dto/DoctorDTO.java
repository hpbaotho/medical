/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.medical.dto;

import entities.medical.Persons;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author Piotrek
 */
public class DoctorDTO implements Serializable {

    private BigInteger idPersons;
    private String name;
    private String surname;
    private String role;

    public DoctorDTO() {
        this.idPersons = null;
        this.name = null;
        this.surname = null;
        this.role = null;
    }

    public DoctorDTO(Persons personsEntity) {
        this.idPersons = personsEntity.getIdPersons();
        this.name = null;
        this.surname = null;
        this.role = personsEntity.getRole();
    }

    public BigInteger getIdPersons() {
        return idPersons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }
}
