/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.stateless;

import beans.facades.medical.KeyManifestFacadeLocal;
import beans.facades.medical.PersonsFacadeLocal;
import beans.facades.medical.VisitFacadeLocal;
import entities.medical.KeyManifest;
import entities.medical.Persons;
import entities.medical.Visit;
import entities.medical.dto.Dict;
import entities.medical.dto.VisitDTO;
import exceptions.CryptographyException;
import exceptions.DatabaseException;
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
public class VisitBean implements VisitLocal {

    @EJB
    private ProviderLocal providerBean;
    @EJB
    private VisitFacadeLocal visitFacade;
    @EJB
    private KeyManifestFacadeLocal keyManifestFacade;
    @EJB
    private PersonsFacadeLocal personsFacade;

    public boolean createVisit(VisitDTO visitToAddDTO, BigInteger idPatient, BigInteger idDoctor) throws CryptographyException, DatabaseException {
        if (visitToAddDTO.getDiagnose() != null && visitToAddDTO.getInfo() != null && visitToAddDTO.getDate() != null &&
                idPatient != null && idDoctor != null) {
            Persons doctorEntity = personsFacade.find(idDoctor);
            if (doctorEntity != null && "doctor".equals(doctorEntity.getRole())) {
                Persons parientEntity = personsFacade.find(idPatient);
                if (parientEntity != null) {
                    HashMap<String, String> encryptionRequestData = createCipherTaskData(visitToAddDTO.getDiagnose(), visitToAddDTO.getInfo());
                    CipherTask encryptionRequest = new CipherTask(encryptionRequestData);
                    try {
                        encryptionRequest = providerBean.encrypt(encryptionRequest, Dict.VISIT);
                        Visit visitToAddEntity = new Visit(
                                encryptionRequest.getData().get(Dict.DIAGNOSECOLUMN),
                                encryptionRequest.getData().get(Dict.INFOCOLUMN),
                                visitToAddDTO.getDate(),
                                encryptionRequest.getIv());
                        KeyManifest keyManifest = keyManifestFacade.find(encryptionRequest.getAliasId());
                        if (keyManifest != null) {
                            visitToAddEntity.setKeyManifestId(keyManifest);
                            visitFacade.create(visitToAddEntity);
                            return true;
                        }
                    } catch (GeneralSecurityException ex) {
                        ex.printStackTrace();
                        throw new CryptographyException(ex);
                    } catch (PersistenceException ex) {
                        ex.printStackTrace();
                        throw new DatabaseException(ex);
                    }
                }
            }
        }
        return false;
    }

    public boolean editVisit(VisitDTO visitToEditDTO) throws CryptographyException, DatabaseException {
        if (visitToEditDTO.getIdVisit() != null && visitToEditDTO.getDiagnose() != null &&
                visitToEditDTO.getInfo() != null && visitToEditDTO.getDate() != null) {
            Visit visitToEditEntity = visitFacade.find(visitToEditDTO.getIdVisit());
            if (visitToEditEntity != null) {
                HashMap<String, String> encryptionRequestData = createCipherTaskData(visitToEditDTO.getDiagnose(), visitToEditDTO.getInfo());
                CipherTask encryptionRequest = new CipherTask(encryptionRequestData);
                try {
                    encryptionRequest = providerBean.encrypt(encryptionRequest, Dict.VISIT);
                    visitToEditEntity.setDiagnose(encryptionRequest.getData().get(Dict.DIAGNOSECOLUMN));
                    visitToEditEntity.setInfo(encryptionRequest.getData().get(Dict.INFOCOLUMN));
                    visitToEditEntity.setDate(visitToEditDTO.getDate());
                    visitToEditEntity.setIv(encryptionRequest.getIv());
                    KeyManifest keyManifest = keyManifestFacade.find(encryptionRequest.getAliasId());
                    if (keyManifest != null) {
                        visitToEditEntity.setKeyManifestId(keyManifest);
                        visitFacade.edit(visitToEditEntity);
                        return true;
                    }
                } catch (GeneralSecurityException ex) {
                    ex.printStackTrace();
                    throw new CryptographyException(ex);
                }
            }
        }
        return false;
    }

    public boolean editVisitPatient(VisitDTO visitToEditDTO, BigInteger idPatient) {
        if (visitToEditDTO.getIdVisit() != null && idPatient != null) {
            Persons parientEntity = personsFacade.find(idPatient);
            if (parientEntity != null) {
                Visit visitToEditEntity = visitFacade.find(visitToEditDTO.getIdVisit());
                if (visitToEditEntity != null) {
                    visitToEditEntity.setPatientId(parientEntity);
                    visitFacade.edit(visitToEditEntity);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean editVisitDoctor(VisitDTO visitToEditDTO, BigInteger idDoctor) {
        if (visitToEditDTO.getIdVisit() != null && idDoctor != null) {
            Persons doctorEntity = personsFacade.find(idDoctor);
            if (doctorEntity != null && "doctor".equals(doctorEntity.getRole())) {
                Visit visitToEditEntity = visitFacade.find(visitToEditDTO.getIdVisit());
                if (visitToEditEntity != null) {
                    visitToEditEntity.setDoctorId(doctorEntity);
                    visitFacade.edit(visitToEditEntity);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean removeVisit(BigInteger idVisit){
        if(idVisit!=null){
            Visit visitToRemoveEntity= visitFacade.find(idVisit);
            if(visitToRemoveEntity!=null){
                visitFacade.remove(visitToRemoveEntity);
                return true;
            }
        }
        return false;
    }

    public List<VisitDTO> findByPatient(BigInteger idPatient) throws CryptographyException {
        List<VisitDTO> result = new ArrayList<VisitDTO>();
        if (idPatient != null) {
            Persons patientEntity = personsFacade.find(idPatient);
            if (patientEntity != null) {
                List<Visit> visitEntityList = patientEntity.getVisitPatientList();
                for (int i = 0; i < visitEntityList.size(); i++) {
                    Visit visitEntity = visitEntityList.get(i);
                    HashMap<String, String> decryptionRequestData = createCipherTaskData(visitEntity.getDiagnose(), visitEntity.getInfo());
                    CipherTask decryptionRequest = new CipherTask(decryptionRequestData, visitEntity.getIv(), visitEntity.getKeyManifestId().getIdKeyManifest());
                    try {
                        decryptionRequest = providerBean.decrypt(decryptionRequest);
                        VisitDTO visitDTO= new VisitDTO(visitEntity);
                        visitDTO.setDiagnose(decryptionRequest.getData().get(Dict.DIAGNOSECOLUMN));
                        visitDTO.setInfo(decryptionRequest.getData().get(Dict.INFOCOLUMN));
                        result.add(visitDTO);
                    } catch (GeneralSecurityException ex) {
                        ex.printStackTrace();
                        throw new CryptographyException(ex.getCause());
                    }
                }
            }
        }
        return result;
    }

    private HashMap<String, String> createCipherTaskData(String diagnose, String info) {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(Dict.DIAGNOSECOLUMN, diagnose);
        result.put(Dict.INFOCOLUMN, info);
        return result;
    }
}
