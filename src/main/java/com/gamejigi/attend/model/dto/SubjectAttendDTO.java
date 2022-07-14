package com.gamejigi.attend.model.dto;

public class SubjectAttendDTO {
    // mylecture h1, h2, h3...
    private Integer[] h;

    // student
    private Long studentId;
    private String departName;
    private int grade;
    private String schoolno;
    private int studentState;
    private String name;
    private int late; // 총 지각
    private int absent; // 총 결석
    private int score; // 총 출석

    public Integer[] getH() {
        return h;
    }

    public void setH(Integer[] h) {
        this.h = h;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSchoolno() {
        return schoolno;
    }

    public void setSchoolno(String schoolno) {
        this.schoolno = schoolno;
    }

    public int getStudentState() {
        return studentState;
    }

    public void setStudentState(int studentState) {
        this.studentState = studentState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
