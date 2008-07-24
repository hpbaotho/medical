/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.stateless;

import beans.facades.medical.KeyManifestFacadeLocal;
import beans.facades.medical.PersonsFacadeLocal;
import entities.medical.KeyManifest;
import entities.medical.Persons;
import entities.medical.dto.Dict;
import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import exceptions.PersonsLoginException;
import exceptions.PersonsPeselException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

/**
 *
 * @author Piotrek
 */
@Stateless
public class PersonsBean implements PersonsLocal {

    @EJB
    private KeyManifestFacadeLocal keyManifestFacade;
    @EJB
    private ProviderLocal providerBean;
    @EJB
    private PersonsFacadeLocal personsFacade;

    public boolean createPerson(PersonsDTO personToEditDTO) throws PersonsLoginException, PersonsPeselException, CryptographyException, DatabaseException {
        if (personToEditDTO.getPass() != null && personToEditDTO.getName() != null &&
                personToEditDTO.getSurname() != null && personToEditDTO.getStreet() != null && personToEditDTO.getNumber() > 0 &&
                personToEditDTO.getCity() != null && personToEditDTO.getZip() > 0 && personToEditDTO.getPesel() != null &&
                personToEditDTO.getRole() != null) {
            Persons personToAddEntity = personsFacade.findByPesel(personToEditDTO.getPesel());
            if (personToAddEntity != null) {
                throw new PersonsPeselException();
            }
            HashMap<String, String> encryptionRequestData = createCipherTaskData(personToEditDTO.getName(), personToEditDTO.getSurname(), personToEditDTO.getStreet(), personToEditDTO.getCity());
            CipherTask encryptionRequest = new CipherTask(encryptionRequestData);
            try {
                encryptionRequest = providerBean.encrypt(encryptionRequest, Dict.PERSONS);
                personToAddEntity = new Persons(
                        personToEditDTO.getPass(),
                        encryptionRequest.getData().get(Dict.NAMECOLUMN),
                        encryptionRequest.getData().get(Dict.SURNAMECOLUMN),
                        encryptionRequest.getData().get(Dict.STREETCOLUMN),
                        personToEditDTO.getNumber(),
                        encryptionRequest.getData().get(Dict.CITYCOLUMN),
                        personToEditDTO.getZip(),
                        personToEditDTO.getPhone(),
                        personToEditDTO.getPesel(),
                        personToEditDTO.getRole(),
                        encryptionRequest.getIv());
                KeyManifest keyManifest = keyManifestFacade.find(encryptionRequest.getAliasId());
                if (keyManifest != null) {
                    personToAddEntity.setKeyManifestId(keyManifest);
                    personsFacade.create(personToAddEntity);
                    return true;
                }
            } catch (GeneralSecurityException ex) {
                throw new CryptographyException();
            } catch (PersistenceException ex) {
                throw new DatabaseException();
            }
        }
        return false;
    }

    public boolean editPerson(PersonsDTO personToEditDTO) throws PersonsPeselException, CryptographyException {
        if (personToEditDTO.getIdPersons() != null && personToEditDTO.getPass() != null && personToEditDTO.getName() != null &&
                personToEditDTO.getSurname() != null && personToEditDTO.getStreet() != null && personToEditDTO.getNumber() > 0 &&
                personToEditDTO.getCity() != null && personToEditDTO.getZip() > 0 && personToEditDTO.getPesel() != null &&
                personToEditDTO.getRole() != null) {

            Persons personToEditEntity = personsFacade.findByPesel(personToEditDTO.getPesel());
            if (personToEditEntity != null) {
                if (!personToEditDTO.getIdPersons().equals(personToEditEntity.getIdPersons())) {
                    throw new PersonsPeselException();
                }
            } else {
                personToEditEntity = personsFacade.find(personToEditDTO.getIdPersons());
            }
            HashMap<String, String> encryptionRequestData = createCipherTaskData(personToEditDTO.getName(), personToEditDTO.getSurname(), personToEditDTO.getStreet(), personToEditDTO.getCity());
            CipherTask encryptionRequest = new CipherTask(encryptionRequestData);
            try {
                encryptionRequest = providerBean.encrypt(encryptionRequest, Dict.PERSONS);
                personToEditEntity.setPass(personToEditDTO.getPass());
                personToEditEntity.setName(encryptionRequest.getData().get(Dict.NAMECOLUMN));
                personToEditEntity.setInn(personToEditDTO.getName().charAt(0));
                personToEditEntity.setSurname(encryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                personToEditEntity.setIns(personToEditDTO.getSurname().charAt(0));
                personToEditEntity.setStreet(encryptionRequest.getData().get(Dict.STREETCOLUMN));
                personToEditEntity.setNumber(personToEditDTO.getNumber());
                personToEditEntity.setCity(encryptionRequest.getData().get(Dict.CITYCOLUMN));
                personToEditEntity.setZip(personToEditDTO.getZip());
                personToEditEntity.setPhone(personToEditDTO.getPhone());
                personToEditEntity.setPesel(personToEditDTO.getPesel());
                personToEditEntity.setRole(personToEditDTO.getRole());
                personToEditEntity.setIv(encryptionRequest.getIv());
                KeyManifest keyManifest = keyManifestFacade.find(encryptionRequest.getAliasId());
                if (keyManifest != null) {
                    personToEditEntity.setKeyManifestId(keyManifest);
                    personsFacade.edit(personToEditEntity);
                    return true;
                }
            } catch (GeneralSecurityException ex) {
                throw new CryptographyException();
            }
        }
        return false;
    }

    public List<PersonsDTO> findByInitials(String name, String surname) throws CryptographyException {
        List<PersonsDTO> result = new ArrayList<PersonsDTO>();
        List<Persons> personsList = personsFacade.findByInitials(name.charAt(0), surname.charAt(0));
        for (int i = 0; i < personsList.size(); i++) {
            Persons person = personsList.get(i);
            HashMap<String, String> decryptionRequestData = createCipherTaskData(person.getName(), person.getSurname(), person.getStreet(), person.getCity());
            CipherTask decryptionRequest = new CipherTask(decryptionRequestData, person.getIv(), person.getKeyManifestId().getIdKeyManifest());
            try {
                decryptionRequest = providerBean.decrypt(decryptionRequest);
                PersonsDTO personDTO = new PersonsDTO(person);
                personDTO.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                personDTO.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                personDTO.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                personDTO.setCity(decryptionRequest.getData().get(Dict.CITYCOLUMN));
                result.add(personDTO);
            } catch (GeneralSecurityException ex) {
                throw new CryptographyException();
            }
        }
        return result;
    }

    public List<PersonsDTO> findByZip(int zip) throws CryptographyException {
        List<PersonsDTO> result = new ArrayList<PersonsDTO>();
        List<Persons> personsList = personsFacade.findByZip(zip);
        for (int i = 0; i < personsList.size(); i++) {
            Persons person = personsList.get(i);
            HashMap<String, String> decryptionRequestData = createCipherTaskData(person.getName(), person.getSurname(), person.getStreet(), person.getCity());
            CipherTask decryptionRequest = new CipherTask(decryptionRequestData, person.getIv(), person.getKeyManifestId().getIdKeyManifest());
            try {
                decryptionRequest = providerBean.decrypt(decryptionRequest);
                PersonsDTO personDTO = new PersonsDTO(person);
                personDTO.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                personDTO.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                personDTO.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                personDTO.setCity(decryptionRequest.getData().get(Dict.CITYCOLUMN));
                result.add(personDTO);
            } catch (GeneralSecurityException ex) {
                throw new CryptographyException();
            }
        }
        return result;
    }

    public PersonsDTO findById(BigInteger idPerson) throws CryptographyException {
        PersonsDTO result = null;
        Persons person = personsFacade.find(idPerson);
        if (person != null) {
            HashMap<String, String> decryptionRequestData = createCipherTaskData(person.getName(), person.getSurname(), person.getStreet(), person.getCity());
            CipherTask decryptionRequest = new CipherTask(decryptionRequestData, person.getIv(), person.getKeyManifestId().getIdKeyManifest());
            try {
                decryptionRequest = providerBean.decrypt(decryptionRequest);
                result = new PersonsDTO(person);
                result.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                result.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                result.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                result.setCity(decryptionRequest.getData().get(Dict.CITYCOLUMN));
            } catch (GeneralSecurityException ex) {
                throw new CryptographyException();
            }
        }
        return result;
    }

    private HashMap<String, String> createCipherTaskData(String name, String surname, String street, String city) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(Dict.NAMECOLUMN, name);
        result.put(Dict.SURNAMECOLUMN, surname);
        result.put(Dict.STREETCOLUMN, street);
        result.put(Dict.CITYCOLUMN, city);
        return result;
    }
}


