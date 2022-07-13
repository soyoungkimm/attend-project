package com.gamejigi.attend.model.dto;

public class StLecDTO {
    private int id;
    private int student_id;
    private int depart_id;
    private String code;
    private String yyyy;
    private int grade;
    private int term;
    private int ismajor;
    private int ischoice;
    private int ispractice;
    private String subject_name;
    private int point;
    private int hour;
    private int lecture_id;
    private int teacher_id;
    private String class_name;
    private String depart_name;
    private String teacher_name;
    private int retake;

    public int getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(int depart_id) {
        this.depart_id = depart_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getYyyy() {
        return yyyy;
    }

    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    public int getIsmajor() {
        return ismajor;
    }

    public void setIsmajor(int ismajor) {
        this.ismajor = ismajor;
    }

    public int getIschoice() {
        return ischoice;
    }

    public void setIschoice(int ischoice) {
        this.ischoice = ischoice;
    }

    public int getIspractice() {
        return ispractice;
    }

    public void setIspractice(int ispractice) {
        this.ispractice = ispractice;
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

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(int lecture_id) {
        this.lecture_id = lecture_id;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getRetake() {
        return retake;
    }

    public void setRetake(int retake) {
        this.retake = retake;
    }
}
