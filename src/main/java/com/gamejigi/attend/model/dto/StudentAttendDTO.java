package com.gamejigi.attend.model.dto;

public class StudentAttendDTO {

    // mylecture h1, h2, h3...
    private Integer[] h;

    private String subject_name;
    private int grade;
    private int point;
    private String class_name;
    private int normhour;
    private int late; // 총 지각
    private int absent; // 총 결석
    private int score; // 총 출석
    private int lecture_id;

    public Integer[] getH() {
        return h;
    }

    public void setH(Integer[] h) {
        this.h = h;
    }


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getNormhour() {
        return normhour;
    }

    public void setNormhour(int normhour) {
        this.normhour = normhour;
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

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(int lecture_id) {
        this.lecture_id = lecture_id;
    }
}
