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
import entities.medical.dto.DoctorDTO;
import entities.medical.dto.PersonsDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import exceptions.DoctorRemoveException;
import exceptions.KeyManifestException;
import exceptions.PersonsPeselException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
    @Resource
    private SessionContext ctx;

    @RolesAllowed(value = {"nurse"})
    public boolean createPerson(PersonsDTO personToAddDTO) throws PersonsPeselException, CryptographyException, DatabaseException {
        if (personToAddDTO.getPass() != null && personToAddDTO.getName() != null &&
                personToAddDTO.getSurname() != null && personToAddDTO.getStreet() != null && personToAddDTO.getNumber() != null &&
                personToAddDTO.getCity() != null && personToAddDTO.getZip() > 0 && personToAddDTO.getPesel() != null &&
                personToAddDTO.getRole() != null) {
            Persons personToAddEntity = personsFacade.findByPesel(personToAddDTO.getPesel());
            if (personToAddEntity != null) {
                throw new PersonsPeselException();
            }
            HashMap<String, String> encryptionRequestData = createCipherTaskData(personToAddDTO.getName(), personToAddDTO.getSurname(), personToAddDTO.getStreet(), personToAddDTO.getPhone());
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
                        personToAddDTO.getCity(),
                        personToAddDTO.getZip(),
                        encryptionRequest.getData().get(Dict.PHONECOLUMN),
                        personToAddDTO.getPesel(),
                        personToAddDTO.getRole(),
                        encryptionRequest.getIv());
                KeyManifest keyManifest = keyManifestFacade.find(encryptionRequest.getAliasId());
                if (keyManifest != null) {
                    keyManifestFacade.refresh(keyManifest);
                    if (keyManifest.getIdKeyManifest().equals(encryptionRequest.getAliasId())) {
                        personToAddEntity.setKeyManifestId(keyManifest);
                        personsFacade.create(personToAddEntity);
                        return true;
                    }
                    throw new KeyManifestException();
                }
            } catch (GeneralSecurityException ex) {
                ex.printStackTrace();
                throw new CryptographyException(ex.getCause());
            } catch (PersistenceException ex) {
                ex.printStackTrace();
                throw new DatabaseException();
            }
        }
        return false;
    }

    @RolesAllowed(value = {"doctor", "nurse", "patient"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public boolean editPerson(PersonsDTO personToEditDTO) throws PersonsPeselException, CryptographyException {
        if (personToEditDTO.getIdPersons() != null && personToEditDTO.getPass() != null && personToEditDTO.getName() != null &&
                personToEditDTO.getSurname() != null && personToEditDTO.getStreet() != null && personToEditDTO.getNumber() != null &&
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
            if (personToEditEntity != null) {
                HashMap<String, String> encryptionRequestData = createCipherTaskData(personToEditDTO.getName(), personToEditDTO.getSurname(), personToEditDTO.getStreet(), personToEditDTO.getPhone());
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
                    personToEditEntity.setCity(personToEditDTO.getCity());
                    personToEditEntity.setZip(personToEditDTO.getZip());
                    personToEditEntity.setPhone(encryptionRequest.getData().get(Dict.PHONECOLUMN));
                    personToEditEntity.setPesel(personToEditDTO.getPesel());
                    personToEditEntity.setRole(personToEditDTO.getRole());
                    personToEditEntity.setIv(encryptionRequest.getIv());
                    KeyManifest keyManifest = keyManifestFacade.find(encryptionRequest.getAliasId());
                    if (keyManifest != null) {
                        keyManifestFacade.refresh(keyManifest);
                        if (keyManifest.getIdKeyManifest().equals(encryptionRequest.getAliasId())) {
                            personToEditEntity.setKeyManifestId(keyManifest);
                            personsFacade.edit(personToEditEntity);
                            return true;
                        }
                        throw new KeyManifestException();
                    }
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                    throw new CryptographyException(ex.getCause());
                }
            }
        }
        return false;
    }

    @RolesAllowed(value = {"nurse"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public boolean removePatient(BigInteger idPatientToRemove) {
        if (idPatientToRemove != null) {
            Persons patientEntity = personsFacade.find(idPatientToRemove);
            if (patientEntity != null && !"doctor".equals(patientEntity.getRole())) {
                personsFacade.remove(patientEntity);
                return true;
            }
        }
        return false;
    }

    @RolesAllowed(value = {"nurse"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public boolean removeDoctor(BigInteger idDoctorToRemove) throws DoctorRemoveException {
        if (idDoctorToRemove != null) {
            Persons doctorEntity = personsFacade.find(idDoctorToRemove);
            if (doctorEntity != null && "doctor".equals(doctorEntity.getRole())) {
                if (doctorEntity.getVisitDocList().isEmpty()) {
                    personsFacade.remove(doctorEntity);
                    return true;
                }
                throw new DoctorRemoveException();
            }
        }
        return false;
    }

    @RolesAllowed(value = {"doctor","nurse"})
    public List<PersonsDTO> findPersonByInitials(String name, String surname) throws CryptographyException {
        List<PersonsDTO> result = new ArrayList<PersonsDTO>();
        if (name != null && surname != null) {
            List<Persons> personsEntityList = personsFacade.findByInitials(name.charAt(0), surname.charAt(0));
            for (int i = 0; i < personsEntityList.size(); i++) {
                Persons personEntity = personsEntityList.get(i);
                personsFacade.refresh(personEntity);
                HashMap<String, String> decryptionRequestData = createCipherTaskData(personEntity.getName(), personEntity.getSurname(), personEntity.getStreet(), personEntity.getPhone());
                CipherTask decryptionRequest = new CipherTask(decryptionRequestData, personEntity.getIv(), personEntity.getKeyManifestId().getIdKeyManifest());
                try {
                    decryptionRequest = providerBean.decrypt(decryptionRequest);
                    PersonsDTO personDTO = new PersonsDTO(personEntity);
                    personDTO.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                    personDTO.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                    personDTO.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                    personDTO.setPhone(decryptionRequest.getData().get(Dict.PHONECOLUMN));
                    result.add(personDTO);
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                    throw new CryptographyException(ex.getCause());
                }
            }
        }
        return result;
    }

    @RolesAllowed(value = {"doctor","nurse"})
    public PersonsDTO findPersonByPesel(String pesel) throws CryptographyException {
        PersonsDTO result = null;
        if (pesel != null) {
            Persons personEntity = personsFacade.findByPesel(pesel);
            if (personEntity != null) {
                personsFacade.refresh(personEntity);
                HashMap<String, String> decryptionRequestData = createCipherTaskData(personEntity.getName(), personEntity.getSurname(), personEntity.getStreet(), personEntity.getPhone());
                CipherTask decryptionRequest = new CipherTask(decryptionRequestData, personEntity.getIv(), personEntity.getKeyManifestId().getIdKeyManifest());
                try {
                    decryptionRequest = providerBean.decrypt(decryptionRequest);
                    result = new PersonsDTO(personEntity);
                    result.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                    result.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                    result.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                    result.setPhone(decryptionRequest.getData().get(Dict.PHONECOLUMN));
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                    throw new CryptographyException(ex.getCause());
                }
            }
        }
        return result;
    }

    @RolesAllowed(value = {"doctor","nurse","patient"})
    public PersonsDTO findPersonById(BigInteger idPerson) throws CryptographyException {
        PersonsDTO result = null;
        if (idPerson != null) {
            Persons personEntity = personsFacade.find(idPerson);
            if (personEntity != null) {
                personsFacade.refresh(personEntity);
                HashMap<String, String> decryptionRequestData = createCipherTaskData(personEntity.getName(), personEntity.getSurname(), personEntity.getStreet(), personEntity.getPhone());
                CipherTask decryptionRequest = new CipherTask(decryptionRequestData, personEntity.getIv(), personEntity.getKeyManifestId().getIdKeyManifest());
                try {
                    decryptionRequest = providerBean.decrypt(decryptionRequest);
                    result = new PersonsDTO(personEntity);
                    result.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                    result.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                    result.setStreet(decryptionRequest.getData().get(Dict.STREETCOLUMN));
                    result.setPhone(decryptionRequest.getData().get(Dict.PHONECOLUMN));
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                    throw new CryptographyException(ex.getCause());
                }
            }
        }
        return result;
    }

    @RolesAllowed(value = {"doctor","nurse","patient"})
    public List<DoctorDTO> findDoctors() throws CryptographyException {
        List<DoctorDTO> result = new ArrayList<DoctorDTO>();
        List<Persons> doctorsEntityList = personsFacade.findByRole("doctor");
        for (int i = 0; i < doctorsEntityList.size(); i++) {
            Persons doctorEntity = doctorsEntityList.get(i);
            personsFacade.refresh(doctorEntity);
            HashMap<String, String> decryptionRequestData = new HashMap<String, String>();
            decryptionRequestData.put(Dict.NAMECOLUMN, doctorEntity.getName());
            decryptionRequestData.put(Dict.SURNAMECOLUMN, doctorEntity.getSurname());
            CipherTask decryptionRequest = new CipherTask(decryptionRequestData, doctorEntity.getIv(), doctorEntity.getKeyManifestId().getIdKeyManifest());
            try {
                decryptionRequest = providerBean.decrypt(decryptionRequest);
                DoctorDTO doctorDTO = new DoctorDTO(doctorEntity);
                doctorDTO.setName(decryptionRequest.getData().get(Dict.NAMECOLUMN));
                doctorDTO.setSurname(decryptionRequest.getData().get(Dict.SURNAMECOLUMN));
                result.add(doctorDTO);
            } catch (GeneralSecurityException ex) {
                ex.printStackTrace();
                throw new CryptographyException(ex.getCause());
            }
        }

        return result;
    }

    @RolesAllowed(value = {"doctor","nurse","patient"})
    public BigInteger getLoggedUserId() {
        Principal loggedUser;
        loggedUser = ctx.getCallerPrincipal();
        Persons loggedUserEntity = personsFacade.findByPesel(loggedUser.getName());
        if (loggedUserEntity != null) {
            personsFacade.refresh(loggedUserEntity);
            return loggedUserEntity.getIdPersons();
        }
        return null;
    }

    private HashMap<String, String> createCipherTaskData(String name, String surname, String street, String phone) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(Dict.NAMECOLUMN, name);
        result.put(Dict.SURNAMECOLUMN, surname);
        result.put(Dict.STREETCOLUMN, street);
        result.put(Dict.PHONECOLUMN, phone);
        return result;
    }
}


