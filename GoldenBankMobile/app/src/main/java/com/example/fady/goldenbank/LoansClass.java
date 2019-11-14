package com.example.fady.goldenbank;

import java.io.Serializable;

public class LoansClass implements Serializable {
    private int icon,id;
    private String title,description;
    double rate, amount;

    public LoansClass(){
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LoansClass(int icon, String description){
        this.icon = icon;
        this.description = description;
    }

    public int getId() {
        return icon;
    }

    public void setId(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
