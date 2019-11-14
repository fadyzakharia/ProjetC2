/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goldenbank;
import java.sql.*;
import static packConnection.ConnectionDB.connectToDB;

/**
 *
 * @author FADY
 */
public class Validate {
    public static boolean checkUser(String username,String password) 
    {
        boolean st =false;
        Connection con = null;
        try {
             con = connectToDB();
            //creating connection with the database
            PreparedStatement ps = con.prepareStatement("select * from admins where username=? and password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs =ps.executeQuery();
            st = rs.next();

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return st;                 
    }   
}
