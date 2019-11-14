package com.example.fady.goldenbank;

public class ProfileClass {
    private int icon;
    private String line1, line2;

    public ProfileClass(int icon, String line1, String line2){
        this.icon = icon;
        this.line1 = line1;
        this.line2 = line2;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }
}
