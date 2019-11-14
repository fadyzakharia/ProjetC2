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
@Table(name = "products_cards")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductsCards.findAll", query = "SELECT p FROM ProductsCards p"),
    @NamedQuery(name = "ProductsCards.findById", query = "SELECT p FROM ProductsCards p WHERE p.id = :id"),
    @NamedQuery(name = "ProductsCards.findByName", query = "SELECT p FROM ProductsCards p WHERE p.name = :name"),
    @NamedQuery(name = "ProductsCards.findByImage", query = "SELECT p FROM ProductsCards p WHERE p.image = :image"),
    @NamedQuery(name = "ProductsCards.findByDescription", query = "SELECT p FROM ProductsCards p WHERE p.description = :description"),
    @NamedQuery(name = "ProductsCards.findByType", query = "SELECT p FROM ProductsCards p WHERE p.type = :type"),
    @NamedQuery(name = "ProductsCards.findByMonthlyLimit", query = "SELECT p FROM ProductsCards p WHERE p.monthlyLimit = :monthlyLimit"),
    @NamedQuery(name = "ProductsCards.findByDayLimit", query = "SELECT p FROM ProductsCards p WHERE p.dayLimit = :dayLimit")})
public class ProductsCards implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "image")
    private String image;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 30)
    @Column(name = "type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monthly_limit")
    private Float monthlyLimit;
    @Column(name = "day_limit")
    private Float dayLimit;

    public ProductsCards() {
    }

    public ProductsCards(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(Float monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public Float getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(Float dayLimit) {
        this.dayLimit = dayLimit;
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
        if (!(object instanceof ProductsCards)) {
            return false;
        }
        ProductsCards other = (ProductsCards) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "goldenbank.ProductsCards[ id=" + id + " ]";
    }
    
}
