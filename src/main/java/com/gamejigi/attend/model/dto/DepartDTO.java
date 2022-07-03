package com.gamejigi.attend.model.dto;

public class DepartDTO {
    private int id;
    private String name;
    private int classNum;
    private int gradeSystem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassNum() {
        return classNum;
    }

    public void setClassNum(int classNum) {
        this.classNum = classNum;
    }

    public int getGradeSystem() {
        return gradeSystem;
    }

    public void setGradeSystem(int gradeSystem) {
        this.gradeSystem = gradeSystem;
    }
}
