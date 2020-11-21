package com.example.orahi.model;

public class GraphModel {

    String month;
    String state;

    public GraphModel(String month, String state) {
        this.month = month;
        this.state = state;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}


