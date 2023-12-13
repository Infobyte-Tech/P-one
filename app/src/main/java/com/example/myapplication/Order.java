package com.example.myapplication;

public class Order {
    private String token;
    private int contractValue;
    private String text;

    public Order(String token, int contractValue, String text) {
        this.token = token;
        this.contractValue = contractValue;
        this.text = text;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getContractValue() {
        return contractValue;
    }

    public void setContractValue(int contractValue) {
        this.contractValue = contractValue;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
