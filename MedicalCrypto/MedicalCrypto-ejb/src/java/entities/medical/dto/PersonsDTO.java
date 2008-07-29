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
public class PersonsDTO implements Serializable {

    private BigInteger idPersons;
    private String pass;
    private String name;
    private String surname;
    private String street;
    private int number;
    private String city;
    private int zip;
    private String phone;
    private BigInteger pesel;
    private String role;

    public PersonsDTO() {
        this.idPersons = null;
        this.pass = null;
        this.name = null;
        this.surname = null;
        this.street = null;
        this.number = -1;
        this.city = null;
        this.zip = -1;
        this.phone = null;
        this.pesel = null;
        this.role = null;
    }

    public PersonsDTO(Persons personsEntity) {
        this.idPersons = personsEntity.getIdPersons();
        this.pass = personsEntity.getPass();
        this.name = null;
        this.surname = null;
        this.street = null;
        this.number = personsEntity.getNumber();
        this.city = personsEntity.getCity();
        this.zip = personsEntity.getZip();
        this.phone = null;
        this.pesel = personsEntity.getPesel();
        this.role = personsEntity.getRole();
    }

    public PersonsDTO(String pass, String name, String surname, String street, int number,
            String city, int zip, String phone, BigInteger pesel, String role) {
        this.idPersons = null;
        this.pass = pass;
        this.name = name;
        this.surname = surname;
        this.street = street;
        this.number = number;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.pesel = this.flipPesel(pesel);
        this.role = role;
    }

    public BigInteger getIdPersons() {
        return idPersons;
    }

    public void setIdPersons(BigInteger idPersons) {
        this.idPersons = idPersons;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigInteger getPesel() {
        return pesel;
    }

    public BigInteger getFlippedPesel() {
        return this.flipPesel(pesel);
    }

    public void setPesel(BigInteger pesel) {
        this.pesel = pesel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private BigInteger flipPesel(BigInteger pesel) {
        char[] peselToFlip = pesel.toString().toCharArray();
        char[] flippedPesel = new char[peselToFlip.length];
        for (int i = peselToFlip.length - 1, j = 0; i >= 0; i--, j++) {
            flippedPesel[j] = peselToFlip[i];
        }
        return new BigInteger(new String(flippedPesel));
    }
}
