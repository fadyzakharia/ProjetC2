package com.example.fady.goldenbank;
import com.android.volley.RequestQueue;
import java.util.Date;

public class Clients {
    private String firstName, lastName, username, password= "";
    private String email, image = "";
    private Date dateofBirth;
    private int id;
    private long phoneNumber;

    private Connection conx = new Connection();
    RequestQueue requestQueue;

    public Clients(){    }

    public Clients(int id, String firstName, String lastName, String username,
                   String password, String email, String image, Date dateofBirth,
                   long phoneNumber){

        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.image = image;
        this.dateofBirth = dateofBirth;

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
