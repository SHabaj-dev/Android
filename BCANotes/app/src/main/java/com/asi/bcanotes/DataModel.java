package com.asi.bcanotes;

public class DataModel {
    private String name;


    public DataModel(){

    }

    public DataModel(String semester) {
        this.name = semester;
    }

    public String getSemester() {
        return name;
    }

    public void setSemester(String semester) {
        this.name = semester;
    }
}
