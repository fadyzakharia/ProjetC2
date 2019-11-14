/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldenbank;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rhea
 */
@Entity
@Table(name = "atm_branch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AtmBranch.findAll", query = "SELECT a FROM AtmBranch a"),
    @NamedQuery(name = "AtmBranch.findById", query = "SELECT a FROM AtmBranch a WHERE a.id = :id"),
    @NamedQuery(name = "AtmBranch.findByLongitude", query = "SELECT a FROM AtmBranch a WHERE a.longitude = :longitude"),
    @NamedQuery(name = "AtmBranch.findByLatitude", query = "SELECT a FROM AtmBranch a WHERE a.latitude = :latitude"),
    @NamedQuery(name = "AtmBranch.findByGenre", query = "SELECT a FROM AtmBranch a WHERE a.genre = :genre"),
    @NamedQuery(name = "AtmBranch.findByNature", query = "SELECT a FROM AtmBranch a WHERE a.nature = :nature"),
    @NamedQuery(name = "AtmBranch.findByDescription", query = "SELECT a FROM AtmBranch a WHERE a.description = :description")})
public class AtmBranch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "longitude")
    private Float longitude;
    @Column(name = "latitude")
    private Float latitude;
    @Size(max = 30)
    @Column(name = "genre")
    private String genre;
    @Size(max = 30)
    @Column(name = "nature")
    private String nature;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;

    public AtmBranch() {
    }

    public AtmBranch(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtmBranch)) {
            return false;
        }
        AtmBranch other = (AtmBranch) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "goldenbank.AtmBranch[ id=" + id + " ]";
    }
    
}
