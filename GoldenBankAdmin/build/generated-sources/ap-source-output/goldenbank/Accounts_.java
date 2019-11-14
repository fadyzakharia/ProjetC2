package goldenbank;

import goldenbank.BankBranch;
import goldenbank.ClientCard;
import goldenbank.Clients;
import goldenbank.Currency;
import goldenbank.Transactions;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-11T21:03:43")
@StaticMetamodel(Accounts.class)
public class Accounts_ { 

    public static volatile SingularAttribute<Accounts, BankBranch> branchId;
    public static volatile SingularAttribute<Accounts, Clients> clientId;
    public static volatile ListAttribute<Accounts, ClientCard> clientCardList;
    public static volatile SingularAttribute<Accounts, Long> id;
    public static volatile SingularAttribute<Accounts, Float> credit;
    public static volatile SingularAttribute<Accounts, Float> debit;
    public static volatile SingularAttribute<Accounts, String> type;
    public static volatile SingularAttribute<Accounts, Currency> currencyId;
    public static volatile ListAttribute<Accounts, Transactions> transactionsList;
    public static volatile ListAttribute<Accounts, Transactions> transactionsList1;

}