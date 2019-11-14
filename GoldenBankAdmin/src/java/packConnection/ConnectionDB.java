/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Rhea
 */
public class ConnectionDB {
    public ConnectionDB(){ }
     
    public static Connection connectToDB(){
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/goldenbankdb";
         
            Connection connection = DriverManager.getConnection(url, "postgres", "rheasaade");
            return connection;
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
