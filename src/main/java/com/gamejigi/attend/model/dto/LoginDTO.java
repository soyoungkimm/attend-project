package com.gamejigi.attend.model.dto;

public class LoginDTO {
    private int id;
    private String uid;
    private String pwd;
    private String name;
    private int kind;
    private String pic;
    private String picPath;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
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
