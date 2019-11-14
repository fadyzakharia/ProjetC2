package com.example.fady.goldenbank;

public class Accounts {
    private long id;
    private int branchId, clientId, currencyId;
    private float debit, credit;
    private String type;

    public Accounts(){}

    public Accounts(long id, int branchId, int clientId, int currencyId, float debit, float credit, String type){
        this.id = id;
        this.branchId = branchId;
        this.clientId = clientId;
        this.currencyId = currencyId;
        this.debit = debit;
        this.credit = credit;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public float getDebit() {
        return debit;
    }

    public void setDebit(float debit) {
        this.debit = debit;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
