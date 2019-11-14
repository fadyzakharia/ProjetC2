package goldenbank;

import goldenbank.Accounts;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-11T21:03:43")
@StaticMetamodel(Currency.class)
public class Currency_ { 

    public static volatile ListAttribute<Currency, Accounts> accountsList;
    public static volatile SingularAttribute<Currency, Float> rate;
    public static volatile SingularAttribute<Currency, String> name;
    public static volatile SingularAttribute<Currency, Integer> id;

}