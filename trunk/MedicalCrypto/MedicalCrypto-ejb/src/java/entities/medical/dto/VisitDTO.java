/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.medical.dto;

import entities.medical.Visit;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Piotrek
 */
public class VisitDTO implements Serializable, Comparable {

    private BigInteger idVisit;
    private String diagnose;
    private String info;
    private Date date;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//    private String patientName;
//    private String patientSurname;
//    private String doctorName;
//    private String doctorSurname;
    public VisitDTO() {
        this.idVisit = null;
        this.diagnose = null;
        this.info = null;
        this.date = null;
//        this.patientName = null;
//        this.patientSurname = null;
//        this.doctorName = null;
//        this.doctorSurname = null;
    }

    public VisitDTO(Visit visitEntity) {
        this.idVisit = visitEntity.getIdVisit();
        this.diagnose = null;
        this.info = null;
        this.date = visitEntity.getDate();
//        this.patientName = null;
//        this.patientSurname = null;
//        this.doctorName = null;
//        this.doctorSurname = null;
    }

    public VisitDTO(String diagnose, String info, Date date) {//, String patientName, String patientSurname, String doctorName, String doctorSurname) {
        this.idVisit = null;
        this.diagnose = diagnose;
        this.info = info;
        this.date = date;
//        this.patientName = patientName;
//        this.patientSurname = patientSurname;
//        this.doctorName = doctorName;
//        this.doctorSurname = doctorSurname;
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

    @Override
    public String toString() {
        return df.format(date);
    }

    public int compareTo(Object o) {
        VisitDTO compare = (VisitDTO) o;
        if (date.before(compare.getDate())) {
            return -1;
        } else if (date.after(compare.getDate())) {
            return 1;
        } else {
            return 0;
        }
    }
//
//    public String getPatientName() {
//        return patientName;
//    }
//
//    public void setPatientName(String patientName) {
//        this.patientName = patientName;
//    }
//
//    public String getPatientSurname() {
//        return patientSurname;
//    }
//
//    public void setPatientSurname(String patientSurname) {
//        this.patientSurname = patientSurname;
//    }
//
//    public String getDoctorName() {
//        return doctorName;
//    }
//
//    public void setDoctorName(String doctorName) {
//        this.doctorName = doctorName;
//    }
//
//    public String getDoctorSurname() {
//        return doctorSurname;
//    }
//
//    public void setDoctorSurname(String doctorSurname) {
//        this.doctorSurname = doctorSurname;
//    }
}
