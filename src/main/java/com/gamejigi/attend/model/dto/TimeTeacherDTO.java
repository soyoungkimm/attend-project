package com.gamejigi.attend.model.dto;

public class TimeTeacherDTO {
    private String departName;
    private String teacherName;
    private int teacherKind;
    private int subjectCount;
    private int weekTeachHour;
    private int weekTeachDay;

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTeacherKind() {
        return teacherKind;
    }

    public void setTeacherKind(int teacherKind) {
        this.teacherKind = teacherKind;
    }

    public int getSubjectCount() {
        return subjectCount;
    }

    public void setSubjectCount(int subjectCount) {
        this.subjectCount = subjectCount;
    }

    public int getWeekTeachHour() {
        return weekTeachHour;
    }

    public void setWeekTeachHour(int weekTeachHour) {
        this.weekTeachHour = weekTeachHour;
    }

    public int getWeekTeachDay() {
        return weekTeachDay;
    }

    public void setWeekTeachDay(int weekTeachDay) {
        this.weekTeachDay = weekTeachDay;
    }
}
