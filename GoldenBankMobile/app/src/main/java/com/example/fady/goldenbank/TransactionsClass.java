package com.example.fady.goldenbank;

public class TransactionsClass implements Comparable<TransactionsClass> {
    private String type, location;
    private String date, currency;
    private String amount;

    public TransactionsClass(String date, String type, String location, String amount, String currency){
        this.date = date;
        this.type = type;
        this.location = location;
        this.amount = amount;
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public int compareTo(TransactionsClass another) {
        return getDate().compareTo(another.getDate());
    }
}
