package com.example.codeledge_v2.ui.categories;

class CategoriesData {
    private String name;
    private int totalPrograms;

    public CategoriesData(String name, int totalPrograms) {
        this.name = name;
        this.totalPrograms = totalPrograms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalPrograms() {
        return totalPrograms;
    }

    public void setTotalPrograms(int totalPrograms) {
        this.totalPrograms = totalPrograms;
    }
}
