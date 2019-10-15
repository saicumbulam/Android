package com.example.codeledge;

public class ProgramData {
    private String name;
    private String type;
    private String descr;
    private String efficy;

    public ProgramData(String name, String type, String descr, String efficy) {
        this.name = name;
        this.type = type;
        this.descr = descr;
        this.efficy = efficy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getEfficy() {
        return efficy;
    }

    public void setEfficy(String efficy) {
        this.efficy = efficy;
    }





}