package com.example.fady.goldenbank;

public class MyCardsClass {
    private long cardNumber;
    private String monthCeiling, dayCeiling;
    private String type;

    public MyCardsClass(long cardNumber, String monthCeiling, String dayCeiling, String type){
        this.cardNumber = cardNumber;
        this.monthCeiling = monthCeiling;
        this.dayCeiling = dayCeiling;
        this.type = type;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMonthCeiling() {
        return monthCeiling;
    }

    public void setMonthCeiling(String monthCeiling) {
        this.monthCeiling = monthCeiling;
    }

    public String getDayCeiling() {
        return dayCeiling;
    }

    public void setDayCeiling(String dayCeiling) {
        this.dayCeiling = dayCeiling;
    }
}
