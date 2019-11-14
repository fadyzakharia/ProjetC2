package goldenbank;

import goldenbank.Accounts;
import goldenbank.ClientCard;
import goldenbank.Currency;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-11T21:03:43")
@StaticMetamodel(Transactions.class)
public class Transactions_ { 

    public static volatile SingularAttribute<Transactions, Date> date;
    public static volatile SingularAttribute<Transactions, Float> amount;
    public static volatile SingularAttribute<Transactions, ClientCard> cardId;
    public static volatile SingularAttribute<Transactions, Accounts> account2;
    public static volatile SingularAttribute<Transactions, Accounts> account1;
    public static volatile SingularAttribute<Transactions, String> location;
    public static volatile SingularAttribute<Transactions, Integer> id;
    public static volatile SingularAttribute<Transactions, String> type;
    public static volatile SingularAttribute<Transactions, Currency> currencyId;

}