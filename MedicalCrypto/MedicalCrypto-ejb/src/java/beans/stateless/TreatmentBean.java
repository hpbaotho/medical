/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.stateless;

import beans.facades.medical.KeyManifestFacadeLocal;
import beans.facades.medical.PersonsFacadeLocal;
import beans.facades.medical.TreatmentFacadeLocal;
import beans.facades.medical.VisitFacadeLocal;
import entities.medical.KeyManifest;
import entities.medical.Persons;
import entities.medical.Treatment;
import entities.medical.Visit;
import entities.medical.dto.Dict;
import entities.medical.dto.TreatmentDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
import exceptions.KeyManifestException;
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
public class TreatmentBean implements TreatmentLocal {

    @EJB
    private PersonsFacadeLocal personsFacade;
    @EJB
    private KeyManifestFacadeLocal keyManifestFacade;
    @EJB
    private TreatmentFacadeLocal treatmentFacade;
    @EJB
    private ProviderLocal providerBean;
    @EJB
    private VisitFacadeLocal visitFacade;

    public boolean createTreatment(TreatmentDTO treatmentToAddDTO, BigInteger idVisit) throws CryptographyException, DatabaseException {
        if (treatmentToAddDTO.getMedicine() != null && treatmentToAddDTO.getDosage() != null && idVisit != null) {
            Visit visitEntity = visitFacade.find(idVisit);
            if (visitEntity != null) {
                HashMap<String, String> encryptionRequestData = createCipherTaskData(treatmentToAddDTO.getMedicine(), treatmentToAddDTO.getDosage());
                CipherTask encryptionRequest = new CipherTask(encryptionRequestData);
                try {
                    encryptionRequest = providerBean.encrypt(encryptionRequest, Dict.TREATMENT);
                    Treatment treatmentToAddEntity = new Treatment(
                            encryptionRequest.getData().get(Dict.MEDICINECOLUMN),
                            encryptionRequest.getData().get(Dict.DOSAGECOLUMN),
                            encryptionRequest.getIv());
                    treatmentToAddEntity.setVisitId(visitEntity);
                    KeyManifest keyManifest = keyManifestFacade.find(encryptionRequest.getAliasId());
                    if (keyManifest != null) {
                        if (keyManifest.getIdKeyManifest().equals(encryptionRequest.getAliasId())) {
                            treatmentToAddEntity.setKeyManifestId(keyManifest);
                            treatmentFacade.create(treatmentToAddEntity);
                            return true;
                        }
                        throw new KeyManifestException();
                    }
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                    throw new CryptographyException(ex);
                } catch (PersistenceException ex) {
                    ex.printStackTrace();
                    throw new DatabaseException();
                }
            }
        }
        return false;
    }

    public boolean editTreatment(TreatmentDTO treatmentToEditDTO) throws CryptographyException {
        if (treatmentToEditDTO.getIdTreatment() != null && treatmentToEditDTO.getMedicine() != null && treatmentToEditDTO.getDosage() != null) {
            Treatment treatmentToEditEntity = treatmentFacade.find(treatmentToEditDTO.getIdTreatment());
            if (treatmentToEditEntity != null) {
                HashMap<String, String> encryptionRequestData = createCipherTaskData(treatmentToEditDTO.getMedicine(), treatmentToEditDTO.getDosage());
                CipherTask encryptionRequest = new CipherTask(encryptionRequestData);
                try {
                    encryptionRequest = providerBean.encrypt(encryptionRequest, Dict.TREATMENT);
                    treatmentToEditEntity.setMedicine(encryptionRequest.getData().get(Dict.MEDICINECOLUMN));
                    treatmentToEditEntity.setDosage(encryptionRequest.getData().get(Dict.DOSAGECOLUMN));
                    treatmentToEditEntity.setIv(encryptionRequest.getIv());
                    KeyManifest keyManifest = keyManifestFacade.find(encryptionRequest.getAliasId());
                    if (keyManifest != null) {
                        if (keyManifest.getIdKeyManifest().equals(encryptionRequest.getAliasId())) {
                            treatmentToEditEntity.setKeyManifestId(keyManifest);
                            treatmentFacade.edit(treatmentToEditEntity);
                            return true;
                        }
                        throw new KeyManifestException();
                    }
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                    throw new CryptographyException(ex);
                }
            }
        }
        return false;
    }

    public boolean removeTreatment(BigInteger idTreatment) {
        if (idTreatment != null) {
            Treatment treatmentToRemoveEntity = treatmentFacade.find(idTreatment);
            if (treatmentToRemoveEntity != null) {
                treatmentFacade.remove(treatmentToRemoveEntity);
                return true;
            }
        }
        return false;
    }

    public List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) throws CryptographyException {
        List<TreatmentDTO> result = new ArrayList<TreatmentDTO>();
        if (idVisit != null) {
            Visit visitEntity = visitFacade.find(idVisit);
            if (visitEntity != null) {
                List<Treatment> treatmentEntityList = visitEntity.getTreatmentList();
                for (int i = 0; i < treatmentEntityList.size(); i++) {
                    Treatment treatmentEntity = treatmentEntityList.get(i);
                    HashMap<String, String> decryptionRequestData = createCipherTaskData(treatmentEntity.getMedicine(), treatmentEntity.getDosage());
                    CipherTask decryptionRequest = new CipherTask(decryptionRequestData, treatmentEntity.getIv(), treatmentEntity.getKeyManifestId().getIdKeyManifest());
                    try {
                        decryptionRequest = providerBean.decrypt(decryptionRequest);
                        TreatmentDTO treatmentDTO = new TreatmentDTO(treatmentEntity);
                        treatmentDTO.setMedicine(decryptionRequest.getData().get(Dict.MEDICINECOLUMN));
                        treatmentDTO.setDosage(decryptionRequest.getData().get(Dict.DOSAGECOLUMN));
                        result.add(treatmentDTO);
                    } catch (GeneralSecurityException ex) {
                        ex.printStackTrace();
                        throw new CryptographyException(ex.getCause());
                    }
                }
            }
        }
        return result;
    }

    public List<TreatmentDTO> findTreatmentByPatient(BigInteger idPatient) throws CryptographyException {
        List<TreatmentDTO> result = new ArrayList<TreatmentDTO>();
        if (idPatient != null) {
            Persons patientEntity = personsFacade.find(idPatient);
            if (patientEntity != null) {
                List<Visit> visitEntityList = patientEntity.getVisitPatientList();
                for (int i = 0; i < visitEntityList.size(); i++) {
                    Visit visitEntity = visitEntityList.get(i);
                    List<Treatment> treatmentEntityList = visitEntity.getTreatmentList();
                    for (int j = 0; j < treatmentEntityList.size(); j++) {
                        Treatment treatmentEntity = treatmentEntityList.get(j);
                        HashMap<String, String> decryptionRequestData = createCipherTaskData(treatmentEntity.getMedicine(), treatmentEntity.getDosage());
                        CipherTask decryptionRequest = new CipherTask(decryptionRequestData, treatmentEntity.getIv(), treatmentEntity.getKeyManifestId().getIdKeyManifest());
                        try {
                            decryptionRequest = providerBean.decrypt(decryptionRequest);
                            TreatmentDTO treatmentDTO = new TreatmentDTO(treatmentEntity);
                            treatmentDTO.setMedicine(decryptionRequest.getData().get(Dict.MEDICINECOLUMN));
                            treatmentDTO.setDosage(decryptionRequest.getData().get(Dict.DOSAGECOLUMN));
                            result.add(treatmentDTO);
                        } catch (GeneralSecurityException ex) {
                            ex.printStackTrace();
                            throw new CryptographyException(ex.getCause());
                        }
                    }
                }
            }
        }
        return result;
    }

    private HashMap<String, String> createCipherTaskData(String medicine, String dosage) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(Dict.MEDICINECOLUMN, medicine);
        result.put(Dict.DOSAGECOLUMN, dosage);
        return result;
    }
}
