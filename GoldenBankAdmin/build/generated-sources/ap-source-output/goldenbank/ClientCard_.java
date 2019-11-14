package goldenbank;

import goldenbank.Accounts;
import goldenbank.Transactions;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-11T21:03:43")
@StaticMetamodel(ClientCard.class)
public class ClientCard_ { 

    public static volatile SingularAttribute<ClientCard, Accounts> accountId;
    public static volatile SingularAttribute<ClientCard, Float> dayLimit;
    public static volatile SingularAttribute<ClientCard, Long> id;
    public static volatile SingularAttribute<ClientCard, String> type;
    public static volatile SingularAttribute<ClientCard, Float> monthlyLimit;
    public static volatile ListAttribute<ClientCard, Transactions> transactionsList;

}