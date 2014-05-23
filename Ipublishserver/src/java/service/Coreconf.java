/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vivekbhusal
 */
@Entity
@Table(name = "CORECONF")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coreconf.findAll", query = "SELECT c FROM Coreconf c"),
    @NamedQuery(name = "Coreconf.findByConfId", query = "SELECT c FROM Coreconf c WHERE c.confId = :confId"),
    @NamedQuery(name = "Coreconf.findByConfTitle", query = "SELECT c FROM Coreconf c WHERE c.confTitle = :confTitle"),
    @NamedQuery(name = "Coreconf.findByAcronym", query = "SELECT c FROM Coreconf c WHERE c.acronym = :acronym"),
    @NamedQuery(name = "Coreconf.findByRank", query = "SELECT c FROM Coreconf c WHERE c.rank = :rank"),
    @NamedQuery(name = "Coreconf.findByChanged", query = "SELECT c FROM Coreconf c WHERE c.changed = :changed"),
    @NamedQuery(name = "Coreconf.findByForcode", query = "SELECT c FROM Coreconf c WHERE c.forcode = :forcode")})
public class Coreconf implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONF_ID")
    private Integer confId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "CONF_TITLE")
    private String confTitle;
    @Size(max = 25)
    @Column(name = "ACRONYM")
    private String acronym;
    @Size(max = 15)
    @Column(name = "RANK")
    private String rank;
    @Column(name = "CHANGED")
    private boolean changed;
    @Size(max = 4)
    @Column(name = "FORCODE")
    private String forcode;

    public Coreconf() {
    }

    public Coreconf(Integer confId) {
        this.confId = confId;
    }

    public Coreconf(Integer confId, String confTitle) {
        this.confId = confId;
        this.confTitle = confTitle;
    }

    public Integer getConfId() {
        return confId;
    }

    public void setConfId(Integer confId) {
        this.confId = confId;
    }

    public String getConfTitle() {
        return confTitle;
    }

    public void setConfTitle(String confTitle) {
        this.confTitle = confTitle;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public boolean getChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public String getForcode() {
        return forcode;
    }

    public void setForcode(String forcode) {
        this.forcode = forcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (confId != null ? confId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coreconf)) {
            return false;
        }
        Coreconf other = (Coreconf) object;
        if ((this.confId == null && other.confId != null) || (this.confId != null && !this.confId.equals(other.confId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "service.Coreconf[ confId=" + confId + " ]";
    }
    
}
