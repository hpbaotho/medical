/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.stateless;

import beans.facades.medical.KeyManifestFacadeLocal;
import beans.facades.medical.TreatmentFacadeLocal;
import beans.facades.medical.VisitFacadeLocal;
import entities.medical.KeyManifest;
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
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceException;

/**
 *
 * @author Piotrek
 */
@Stateless
public class TreatmentBean implements TreatmentLocal {

    @EJB
    private KeyManifestFacadeLocal keyManifestFacade;
    @EJB
    private TreatmentFacadeLocal treatmentFacade;
    @EJB
    private ProviderLocal providerBean;
    @EJB
    private VisitFacadeLocal visitFacade;

    @RolesAllowed(value = {"doctor"})
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
                        keyManifestFacade.refresh(keyManifest);
                        keyManifestFacade.refresh(keyManifest);
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

    @RolesAllowed(value = {"doctor"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
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
                        keyManifestFacade.refresh(keyManifest);
                        keyManifestFacade.refresh(keyManifest);
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

    @RolesAllowed(value = {"doctor"})
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
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

    @RolesAllowed(value = {"doctor", "nurse", "patient"})
    public List<TreatmentDTO> findTreatmentByVisit(BigInteger idVisit) throws CryptographyException {
        List<TreatmentDTO> result = new ArrayList<TreatmentDTO>();
        if (idVisit != null) {
            Visit visitEntity = visitFacade.find(idVisit);
            if (visitEntity != null) {
                visitFacade.refresh(visitEntity);
                List<Treatment> treatmentEntityList = visitEntity.getTreatmentList();
                for (int i = 0; i < treatmentEntityList.size(); i++) {
                    Treatment treatmentEntity = treatmentEntityList.get(i);
                    //treatmentFacade.refresh(treatmentEntity);
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

    private HashMap<String, String> createCipherTaskData(String medicine, String dosage) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(Dict.MEDICINECOLUMN, medicine);
        result.put(Dict.DOSAGECOLUMN, dosage);
        return result;
    }
}
