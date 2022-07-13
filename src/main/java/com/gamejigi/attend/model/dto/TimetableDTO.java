package com.gamejigi.attend.model.dto;

public class TimetableDTO {
    //timetable 테이블
    private int id;
    private int lecture_id;
    private int weekday;
    private int istart;
    private int ihour;
    private int room_id;
    //subject 테이블
    private int subject_grade;
    private int subject_hour;
    private String subject_name;
    //lecture 테이블
    private String lecture_class;
    private int teacher_id;
    //teacher 테이블
    private String teacher_name;
    //room 테이블
    private String room_name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(int lecture_id) {
        this.lecture_id = lecture_id;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getIstart() {
        return istart;
    }

    public void setIstart(int istart) {
        this.istart = istart;
    }

    public int getIhour() {
        return ihour;
    }

    public void setIhour(int ihour) {
        this.ihour = ihour;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getSubject_grade() {
        return subject_grade;
    }

    public void setSubject_grade(int subject_grade) {
        this.subject_grade = subject_grade;
    }

    public int getSubject_hour() {
        return subject_hour;
    }

    public void setSubject_hour(int subject_hour) {
        this.subject_hour = subject_hour;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getLecture_class() {
        return lecture_class;
    }

    public void setLecture_class(String lecture_class) {
        this.lecture_class = lecture_class;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }
}
