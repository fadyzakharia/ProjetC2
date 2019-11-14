/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldenbank;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rhea
 */
@Entity
@Table(name = "transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t"),
    @NamedQuery(name = "Transactions.findById", query = "SELECT t FROM Transactions t WHERE t.id = :id"),
    @NamedQuery(name = "Transactions.findByDate", query = "SELECT t FROM Transactions t WHERE t.date = :date"),
    @NamedQuery(name = "Transactions.findByAmount", query = "SELECT t FROM Transactions t WHERE t.amount = :amount"),
    @NamedQuery(name = "Transactions.findByType", query = "SELECT t FROM Transactions t WHERE t.type = :type"),
    @NamedQuery(name = "Transactions.findByLocation", query = "SELECT t FROM Transactions t WHERE t.location = :location"),
    @NamedQuery(name = "Transactions.findByCardId", query = "SELECT t FROM Transactions t WHERE t.cardId.id = :cardId"),
    @NamedQuery(name = "Transactions.findByAccount1", query = "SELECT t FROM Transactions t WHERE t.account1.id = :account1"),
    @NamedQuery(name = "Transactions.findByAccount2", query = "SELECT t FROM Transactions t WHERE t.account2.id = :account2")})

public class Transactions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Float amount;
    @Size(max = 30)
    @Column(name = "type")
    private String type;
    @Size(max = 80)
    @Column(name = "location")
    private String location;
    @JoinColumn(name = "account1", referencedColumnName = "id")
    @ManyToOne
    private Accounts account1;
    @JoinColumn(name = "account2", referencedColumnName = "id")
    @ManyToOne
    private Accounts account2;
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    @ManyToOne
    private ClientCard cardId;
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    @ManyToOne
    private Currency currencyId;

    public Transactions() {
    }

    public Transactions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Accounts getAccount1() {
        return account1;
    }

    public void setAccount1(Accounts account1) {
        this.account1 = account1;
    }

    public Accounts getAccount2() {
        return account2;
    }

    public void setAccount2(Accounts account2) {
        this.account2 = account2;
    }

    public ClientCard getCardId() {
        return cardId;
    }

    public void setCardId(ClientCard cardId) {
        this.cardId = cardId;
    }
    
    public Currency getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Currency currencyId) {
        this.currencyId = currencyId;
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
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "goldenbank.Transactions[ id=" + id + " ]";
    }
    
}
