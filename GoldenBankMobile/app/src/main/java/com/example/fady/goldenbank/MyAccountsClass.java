package com.example.fady.goldenbank;

public class MyAccountsClass {
    private long accountId;
    private Double balance;
    private String type, currency;

    public MyAccountsClass(){  }

    public MyAccountsClass(long accountId, Double balance, String type, String currency){
        this.accountId = accountId;
        this.balance = balance;
        this.type = type;
        this.currency = currency;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
