/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.crypto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Piotrek
 */
@Entity
@Table(name = "engine")
@NamedQueries({
    @NamedQuery(name = "Engine.findByIdEngine", query = "SELECT e FROM Engine e WHERE e.idEngine = :idEngine"), 
    @NamedQuery(name = "Engine.findByEngine", query = "SELECT e FROM Engine e WHERE e.engine = :engine"), 
    @NamedQuery(name = "Engine.findByKeysize", query = "SELECT e FROM Engine e WHERE e.keysize = :keysize")
})
public class Engine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_engine", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger idEngine;
    @Column(name = "engine", nullable = false)
    private String engine;
    @Column(name = "keysize", nullable = false)
    private int keysize;
    @OneToMany(mappedBy = "engineId")
    private List<Seckey> seckeyList= new ArrayList<Seckey>();

    public Engine() {
    }

    public Engine(String engine, int keysize) {
        this.engine = engine;
        this.keysize = keysize;
    }

    public BigInteger getIdEngine() {
        return idEngine;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getKeysize() {
        return keysize;
    }

    public void setKeysize(int keysize) {
        this.keysize = keysize;
    }

    public List<Seckey> getSeckeyList() {
        return seckeyList;
    }

    public void setSeckeyList(List<Seckey> seckeyList) {
        this.seckeyList = seckeyList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEngine != null ? idEngine.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Engine)) {
            return false;
        }
        Engine other = (Engine) object;
        if ((this.idEngine == null && other.idEngine != null) || (this.idEngine != null && !this.idEngine.equals(other.idEngine))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.medical.Engine[idEngine=" + idEngine + "]";
    }
}
