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

    public boolean createPerson(PersonsDTO personToAddDTO) throws PersonsLoginException, PersonsPeselException, CryptographyException, DatabaseException {
        if (personToAddDTO.getPass() != null && personToAddDTO.getName() != null &&
                personToAddDTO.getSurname() != null && personToAddDTO.getStreet() != null && personToAddDTO.getNumber() > 0 &&
                personToAddDTO.getCity() != null && personToAddDTO.getZip() > 0 && personToAddDTO.getPesel() != null &&
                personToAddDTO.getRole() != null) {
            Persons personToAddEntity = personsFacade.findByPesel(personToAddDTO.getPesel());
            if (personToAddEntity != null) {
                throw new PersonsPeselException();
            }
            HashMap<String, String> encryptionRequestData = createCipherTaskData(personToAddDTO.getName(), personToAddDTO.getSurname(), personToAddDTO.getStreet(), personToAddDTO.getCity(), personToAddDTO.getPhone());
            CipherTask encryptionRequest = new CipherTask(encryptionRequestData);
            try {
                encryptionRequest = providerBean.encrypt(encryptionRequest, Dict.PERSONS);
                personToAddEntity = new Persons(
                        personToAddDTO.getPass(),
                        encryptionRequest.getData().get(Dict.NAMECOLUMN),
                        personToAddDTO.getName().charAt(0),
                        encryptionRequest.getData().get(Dict.SURNAMECOLUMN),
                        personToAddDTO.getSurname().charAt(0),
                        encryptionRequest.getData().get(Dict.STREETCOLUMN),
                        personToAddDTO.getNumber(),
                        encryptionRequest.getData().get(Dict.CITYCOLUMN),
                        personToAddDTO.getZip(),
                        encryptionRequest.getData().get(Dict.PHONECOLUMN),
                        personToAddDTO.getPesel(),
                        personToAddDTO.getRole(),
                        encryptionRequest.getIv());
                KeyManifest keyManifest = keyManifestFacade.find(encryptionRequest.getAliasId());
                if (keyManifest != null) {
                    personToAddEntity.setKeyManifestId(keyManifest);
                    personsFacade.create(personToAddEntity);
                    return true;
                }
            } catch (GeneralSecurityException ex) {
                ex.printStackTrace();
                throw new CryptographyException(ex.getCause());
            } catch (PersistenceException ex) {
                ex.printStackTrace();
                throw new DatabaseException(ex.getCause());
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
            HashMap<String, String> encryptionRequestData = createCipherTaskData(personToEditDTO.getName(), personToEditDTO.getSurname(), personToEditDTO.getStreet(), personToEditDTO.getCity(), personToEditDTO.getPhone());
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
                personToEditEntity.setPhone(encryptionRequest.getData().get(Dict.PHONECOLUMN));
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
                ex.printStackTrace();
                throw new CryptographyException(ex.getCause());
            }
        }
        return false;
    }
    
    // metoda do dokonczenia- kogo mozna usuwac?
    public boolean removePerson(BigInteger idPersonToRemove){
        
        return false;
    }

    public List<PersonsDTO> findPersonByInitials(String name, String surname) throws CryptographyException {
        List<PersonsDTO> result = new ArrayList<PersonsDTO>();
        List<Persons> personsEntityList = personsFacade.findByInitials(name.charAt(0), surname.charAt(0));
        for (int i = 0; i < personsEntityList.size(); i++) {
            Persons personEntity = personsEntityList.get(i);
            HashMap<String, String> decryptionRequestData = createCipherTaskData(personEntity.getName(), personEntity.getSurname(), personEntity.getStreet(), personEntity.getCity(), personEntity.getPhone());
            CipherTask decryptionRequest = new CipherTask(decryptionRequestData, personEntity.getIv(), personEntity.getKeyManifestId().getIdKeyManifest());
            try {
                decryptionRequest = providerBean.decrypt(decryptionRequest);
                PersonsDTO personDTO = new PersonsDTO(personEntity);
                personDTO.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                personDTO.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                personDTO.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                personDTO.setCity(decryptionRequest.getData().get(Dict.CITYCOLUMN));
                personDTO.setPhone(decryptionRequest.getData().get(Dict.PHONECOLUMN));
                result.add(personDTO);
            } catch (GeneralSecurityException ex) {
                ex.printStackTrace();
                throw new CryptographyException(ex.getCause());
            }
        }
        return result;
    }

    public List<PersonsDTO> findPersonByZip(int zip) throws CryptographyException {
        List<PersonsDTO> result = new ArrayList<PersonsDTO>();
        List<Persons> personsEntityList = personsFacade.findByZip(zip);
        for (int i = 0; i < personsEntityList.size(); i++) {
            Persons personEntity = personsEntityList.get(i);
            HashMap<String, String> decryptionRequestData = createCipherTaskData(personEntity.getName(), personEntity.getSurname(), personEntity.getStreet(), personEntity.getCity(), personEntity.getPhone());
            CipherTask decryptionRequest = new CipherTask(decryptionRequestData, personEntity.getIv(), personEntity.getKeyManifestId().getIdKeyManifest());
            try {
                decryptionRequest = providerBean.decrypt(decryptionRequest);
                PersonsDTO personDTO = new PersonsDTO(personEntity);
                personDTO.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                personDTO.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                personDTO.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                personDTO.setCity(decryptionRequest.getData().get(Dict.CITYCOLUMN));
                personDTO.setPhone(decryptionRequest.getData().get(Dict.PHONECOLUMN));
                result.add(personDTO);
            } catch (GeneralSecurityException ex) {
                ex.printStackTrace();
                throw new CryptographyException(ex.getCause());
            }
        }
        return result;
    }
    
    public PersonsDTO findPersonByPesel(BigInteger pesel) throws CryptographyException {
        PersonsDTO result = null;
        Persons personEntity = personsFacade.findByPesel(pesel);
        if (personEntity != null) {
            HashMap<String, String> decryptionRequestData = createCipherTaskData(personEntity.getName(), personEntity.getSurname(), personEntity.getStreet(), personEntity.getCity(), personEntity.getPhone());
            CipherTask decryptionRequest = new CipherTask(decryptionRequestData, personEntity.getIv(), personEntity.getKeyManifestId().getIdKeyManifest());
            try {
                decryptionRequest = providerBean.decrypt(decryptionRequest);
                result = new PersonsDTO(personEntity);
                result.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                result.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                result.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                result.setCity(decryptionRequest.getData().get(Dict.CITYCOLUMN));
                result.setPhone(decryptionRequest.getData().get(Dict.PHONECOLUMN));
            } catch (GeneralSecurityException ex) {
                ex.printStackTrace();
                throw new CryptographyException(ex.getCause());
            }
        }
        return result;
    }

    public PersonsDTO findPersonById(BigInteger idPerson) throws CryptographyException {
        PersonsDTO result = null;
        Persons personEntity = personsFacade.find(idPerson);
        if (personEntity != null) {
            HashMap<String, String> decryptionRequestData = createCipherTaskData(personEntity.getName(), personEntity.getSurname(), personEntity.getStreet(), personEntity.getCity(), personEntity.getPhone());
            CipherTask decryptionRequest = new CipherTask(decryptionRequestData, personEntity.getIv(), personEntity.getKeyManifestId().getIdKeyManifest());
            try {
                decryptionRequest = providerBean.decrypt(decryptionRequest);
                result = new PersonsDTO(personEntity);
                result.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                result.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                result.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                result.setCity(decryptionRequest.getData().get(Dict.CITYCOLUMN));
                result.setPhone(decryptionRequest.getData().get(Dict.PHONECOLUMN));
            } catch (GeneralSecurityException ex) {
                ex.printStackTrace();
                throw new CryptographyException(ex.getCause());
            }
        }
        return result;
    }

    private HashMap<String, String> createCipherTaskData(String name, String surname, String street, String city, String phone) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(Dict.NAMECOLUMN, name);
        result.put(Dict.SURNAMECOLUMN, surname);
        result.put(Dict.STREETCOLUMN, street);
        result.put(Dict.CITYCOLUMN, city);
        result.put(Dict.PHONECOLUMN, phone);
        return result;
    }
}


