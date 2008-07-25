/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.medical.dto;

import entities.medical.Treatment;
import java.io.Serializable;
import java.math.BigInteger;

/**
 *
 * @author Piotrek
 */
public class TreatmentDTO implements Serializable {

    private BigInteger idTreatment;
    private String medicine;
    private String dosage;

    public TreatmentDTO() {
        this.idTreatment = null;
        this.medicine = null;
        this.dosage = null;
    }

    public TreatmentDTO(Treatment treatmentEntity) {
        this.idTreatment = treatmentEntity.getIdTreatment();
        this.medicine = treatmentEntity.getMedicine();
        this.dosage = treatmentEntity.getDosage();
    }

    public TreatmentDTO(String medicine, String dosage) {
        this.idTreatment = null;
        this.medicine = medicine;
        this.dosage = dosage;
    }

    public BigInteger getIdTreatment() {
        return idTreatment;
    }
    
    public void setIdTreatment(BigInteger idTreatment) {
        this.idTreatment = idTreatment;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
