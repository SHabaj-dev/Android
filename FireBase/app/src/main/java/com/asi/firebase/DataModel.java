package com.asi.firebase;

public class DataModel {
    String name;

    public DataModel(){};

    public DataModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
