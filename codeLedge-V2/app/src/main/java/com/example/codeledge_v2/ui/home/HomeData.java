package com.example.codeledge_v2.ui.home;

import android.graphics.drawable.Drawable;

class HomeData{
    private String name;
    private int imgLocation;
    private int totalPrograms;

    public HomeData(String name, int imgLocation, int totalPrograms) {
        this.name = name;
        this.imgLocation = imgLocation;
        this.totalPrograms = totalPrograms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgLocation() {
        return imgLocation;
    }

    public void setImgLocation(int imgLocation) {
        this.imgLocation = imgLocation;
    }

    public int getTotalPrograms() {
        return totalPrograms;
    }

    public void setTotalPrograms(int totalPrograms) {
        this.totalPrograms = totalPrograms;
    }
}
