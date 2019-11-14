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
@Table(name = "products_loans")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductsLoans.findAll", query = "SELECT p FROM ProductsLoans p"),
    @NamedQuery(name = "ProductsLoans.findById", query = "SELECT p FROM ProductsLoans p WHERE p.id = :id"),
    @NamedQuery(name = "ProductsLoans.findByName", query = "SELECT p FROM ProductsLoans p WHERE p.name = :name"),
    @NamedQuery(name = "ProductsLoans.findByImage", query = "SELECT p FROM ProductsLoans p WHERE p.image = :image"),
    @NamedQuery(name = "ProductsLoans.findByDescription", query = "SELECT p FROM ProductsLoans p WHERE p.description = :description"),
    @NamedQuery(name = "ProductsLoans.findByAmount", query = "SELECT p FROM ProductsLoans p WHERE p.amount = :amount"),
    @NamedQuery(name = "ProductsLoans.findByRate", query = "SELECT p FROM ProductsLoans p WHERE p.rate = :rate")})
public class ProductsLoans implements Serializable {
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Float amount;
    @Column(name = "rate")
    private Float rate;

    public ProductsLoans() {
    }

    public ProductsLoans(Integer id) {
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

    public Float getAmount() {
        return amount;
    }
    
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
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
        if (!(object instanceof ProductsLoans)) {
            return false;
        }
        ProductsLoans other = (ProductsLoans) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "goldenbank.ProductsLoans[ id=" + id + " ]";
    }
    
}
