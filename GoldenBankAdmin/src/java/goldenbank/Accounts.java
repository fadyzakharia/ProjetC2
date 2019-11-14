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
@Table(name = "accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Accounts a"),
    @NamedQuery(name = "Accounts.findById", query = "SELECT a FROM Accounts a WHERE a.id = :id"),
    @NamedQuery(name = "Accounts.findByCredit", query = "SELECT a FROM Accounts a WHERE a.credit = :credit"),
    @NamedQuery(name = "Accounts.findByDebit", query = "SELECT a FROM Accounts a WHERE a.debit = :debit"),
    @NamedQuery(name = "Accounts.findByType", query = "SELECT a FROM Accounts a WHERE a.type = :type"),
    @NamedQuery(name = "Accounts.findByClientId", query = "SELECT a FROM Accounts a WHERE a.clientId.id = :client_id ")})
public class Accounts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "credit")
    private Float credit;
    @Column(name = "debit")
    private Float debit;
    @Size(max = 30)
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "accountId")
    private List<ClientCard> clientCardList;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne
    private BankBranch branchId;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne
    private Clients clientId;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne
    private Currency currencyId;
    @OneToMany(mappedBy = "account1")
    private List<Transactions> transactionsList;
    @OneToMany(mappedBy = "account2")
    private List<Transactions> transactionsList1;

    public Accounts() {
    }

    public Accounts(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public Float getDebit() {
        return debit;
    }

    public void setDebit(Float debit) {
        this.debit = debit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public List<ClientCard> getClientCardList() {
        return clientCardList;
    }

    public void setClientCardList(List<ClientCard> clientCardList) {
        this.clientCardList = clientCardList;
    }

    public BankBranch getBranchId() {
        return branchId;
    }

    public void setBranchId(BankBranch branchId) {
        this.branchId = branchId;
    }

    public Clients getClientId() {
        return clientId;
    }

    public void setClientId(Clients clientId) {
        this.clientId = clientId;
    }

    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
    }

    @XmlTransient
    public List<Transactions> getTransactionsList() {
        return transactionsList;
    }

    public void setTransactionsList(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    @XmlTransient
    public List<Transactions> getTransactionsList1() {
        return transactionsList1;
    }

    public void setTransactionsList1(List<Transactions> transactionsList1) {
        this.transactionsList1 = transactionsList1;
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
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "goldenbank.Accounts[ id=" + id + " ]";
    }
    
}
