package com.gamejigi.attend.model.dto;

public class ADControlDTO {
    private int id;
    private int subjecttime;
    private int lecturetime;
    private int mylecturetime;
    private int attendtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjecttime() {
        return subjecttime;
    }

    public void setSubjecttime(int subjecttime) {
        this.subjecttime = subjecttime;
    }

    public int getLecturetime() {
        return lecturetime;
    }

    public void setLecturetime(int lecturetime) {
        this.lecturetime = lecturetime;
    }

    public int getMylecturetime() {
        return mylecturetime;
    }

    public void setMylecturetime(int mylecturetime) {
        this.mylecturetime = mylecturetime;
    }

    public int getAttendtime() {
        return attendtime;
    }

    public void setAttendtime(int attendtime) {
        this.attendtime = attendtime;
    }
}
