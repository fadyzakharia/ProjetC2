package goldenbank;

import goldenbank.Accounts;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-11T21:03:43")
@StaticMetamodel(BankBranch.class)
public class BankBranch_ { 

    public static volatile ListAttribute<BankBranch, Accounts> accountsList;
    public static volatile SingularAttribute<BankBranch, Float> latitude;
    public static volatile SingularAttribute<BankBranch, String> name;
    public static volatile SingularAttribute<BankBranch, String> description;
    public static volatile SingularAttribute<BankBranch, Integer> id;
    public static volatile SingularAttribute<BankBranch, Float> longitude;

}