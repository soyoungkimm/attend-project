package com.gamejigi.attend.model.dto;

public class TimetableDTO {
    private int id;
    private int lecture_id;
    private int weekday;
    private int istart;
    private int ihour;
    private int room_id;

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
}
