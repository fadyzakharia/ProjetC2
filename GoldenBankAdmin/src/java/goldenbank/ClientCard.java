/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldenbank;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Rhea
 */
@Entity
@Table(name = "client_card")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientCard.findAll", query = "SELECT c FROM ClientCard c"),
    @NamedQuery(name = "ClientCard.findById", query = "SELECT c FROM ClientCard c WHERE c.id = :id"),
    @NamedQuery(name = "ClientCard.findByType", query = "SELECT c FROM ClientCard c WHERE c.type = :type"),
    @NamedQuery(name = "ClientCard.findByMonthlyLimit", query = "SELECT c FROM ClientCard c WHERE c.monthlyLimit = :monthlyLimit"),
    @NamedQuery(name = "ClientCard.findByDayLimit", query = "SELECT c FROM ClientCard c WHERE c.dayLimit = :dayLimit"),
    @NamedQuery(name = "ClientCard.findByAccountId", query = "SELECT c FROM ClientCard c WHERE c.accountId.id = :accountId")})
public class ClientCard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 30)
    @Column(name = "type")
    private String type;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monthly_limit")
    private Float monthlyLimit;
    @Column(name = "day_limit")
    private Float dayLimit;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne
    private Accounts accountId;
    @OneToMany(mappedBy = "cardId")
    private List<Transactions> transactionsList;

    public ClientCard() {
    }

    public ClientCard(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Accounts getAccountId() {
        return accountId;
    }

    public void setAccountId(Accounts accountId) {
        this.accountId = accountId;
    }

    @XmlTransient
    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
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
        if (!(object instanceof ClientCard)) {
            return false;
        }
        ClientCard other = (ClientCard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "goldenbank.ClientCard[ id=" + id + " ]";
    }
    
}
