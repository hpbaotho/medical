/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.medical.dto;

import entities.medical.Visit;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author Piotrek
 */
public class VisitDTO implements Serializable {

    private BigInteger idVisit;
    private String diagnose;
    private String info;
    private Date date;

    public VisitDTO() {
        this.idVisit = null;
        this.diagnose = null;
        this.info = null;
        this.date = null;
    }

    public VisitDTO(Visit visitEntity) {
        this.idVisit = visitEntity.getIdVisit();
        this.diagnose = visitEntity.getDiagnose();
        this.info = visitEntity.getInfo();
        this.date = visitEntity.getDate();
    }

    public VisitDTO(String diagnose, String info, Date date) {
        this.idVisit = null;
        this.diagnose = diagnose;
        this.info = info;
        this.date = date;
    }

    public BigInteger getIdVisit() {
        return idVisit;
    }
    
    public void setIdVisit(BigInteger idVisit) {
        this.idVisit = idVisit;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
