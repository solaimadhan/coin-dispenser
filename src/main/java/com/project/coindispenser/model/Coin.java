package com.project.coindispenser.model;

public enum Coin {

    QUARTER(25), DIME(10), NICKEL(5), CENT(1);
    
    int value = 0;
    String strValue = null;
    
    Coin(int val) {
        value = val;
        strValue = String.format("%.2f", (float) value/100);
    }

    public int value() {
        return value;
    }

    public String stringValue() {
        return strValue;
    }
}
