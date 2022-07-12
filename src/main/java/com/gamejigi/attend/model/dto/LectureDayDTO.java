package com.gamejigi.attend.model.dto;

public class LectureDayDTO {
    private Long id;
    private int lectureId; // 강의
    private int roomId; // 강의실
    private int th; // 주차
    private int starth; // n주차, 첫시간 위치 ex) 몇번째 시간(4시간수업, 5주차첫시간=>4*(5-1)+1=17
    private String normdate; // 정상날짜
    private int normstart; // 정상시작시간 ex)1교시 -> 1
    private int normhour; // 정상시간 ex)3시간 -> 3
    private int normstate; // 0:정상,1:결강,2:휴강,3:보강
    private String restdate; // 보강날짜
    private int reststart; // 보강시작시간
    private int resthour; // 보강시간
    private int reststate; // 0:정상,1:결강,2:휴강,3:보강
    private int state; // 0:신청,1:취소,2:반려,3:학과장승인,4:최종승인


    // lecture
    //private int subjectId;
    private String subjectName;
    //private int teacherId;
    private String teacherName;
    private String ban;

    // room
    private String roomName;
    private String buildingName;

    // student_num
    private int studentNum;

    // depart
    //private int depart_id;
    private String departName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getTh() {
        return th;
    }

    public void setTh(int th) {
        this.th = th;
    }

    public int getStarth() {
        return starth;
    }

    public void setStarth(int starth) {
        this.starth = starth;
    }

    public String getNormdate() {
        return normdate;
    }

    public void setNormdate(String normdate) {
        this.normdate = normdate;
    }

    public int getNormstart() {
        return normstart;
    }

    public void setNormstart(int normstart) {
        this.normstart = normstart;
    }

    public int getNormhour() {
        return normhour;
    }

    public void setNormhour(int normhour) {
        this.normhour = normhour;
    }

    public int getNormstate() {
        return normstate;
    }

    public void setNormstate(int normstate) {
        this.normstate = normstate;
    }

    public String getRestdate() {
        return restdate;
    }

    public void setRestdate(String restdate) {
        this.restdate = restdate;
    }

    public int getReststart() {
        return reststart;
    }

    public void setReststart(int reststart) {
        this.reststart = reststart;
    }

    public int getResthour() {
        return resthour;
    }

    public void setResthour(int resthour) {
        this.resthour = resthour;
    }

    public int getReststate() {
        return reststate;
    }

    public void setReststate(int reststate) {
        this.reststate = reststate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

//    public int getSubjectId() {
//        return subjectId;
//    }
//
//    public void setSubjectId(int subjectId) {
//        this.subjectId = subjectId;
//    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

//    public int getTeacherId() {
//        return teacherId;
//    }
//
//    public void setTeacherId(int teacherId) {
//        this.teacherId = teacherId;
//    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

//    public int getDepart_id() {
//        return depart_id;
//    }
//
//    public void setDepart_id(int depart_id) {
//        this.depart_id = depart_id;
//    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String depart_name) {
        this.departName = depart_name;
    }
}
