package goldenbank;

import goldenbank.Accounts;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-11T21:03:43")
@StaticMetamodel(Clients.class)
public class Clients_ { 

    public static volatile SingularAttribute<Clients, String> firstName;
    public static volatile SingularAttribute<Clients, String> lastName;
    public static volatile SingularAttribute<Clients, String> image;
    public static volatile ListAttribute<Clients, Accounts> accountsList;
    public static volatile SingularAttribute<Clients, String> password;
    public static volatile SingularAttribute<Clients, BigInteger> phoneNumber;
    public static volatile SingularAttribute<Clients, Date> dateOfBirth;
    public static volatile SingularAttribute<Clients, Integer> id;
    public static volatile SingularAttribute<Clients, String> email;
    public static volatile SingularAttribute<Clients, String> username;

}