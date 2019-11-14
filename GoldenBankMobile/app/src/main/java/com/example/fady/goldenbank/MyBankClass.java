package com.example.fady.goldenbank;

public class MyBankClass {
    private int icon;
    private String title;

    public MyBankClass(){
    }

    public MyBankClass(int icon, String title){
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
