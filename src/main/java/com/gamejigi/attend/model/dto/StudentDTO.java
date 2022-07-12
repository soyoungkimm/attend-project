package com.gamejigi.attend.model.dto;

public class StudentDTO {
    private Long id;
    private int depart_id;
    private int grade;
    private String ban;
    private String schoolno;
    private String pwd;
    private String phone;
    private int gender;
    private String name;
    private int state;
    private String pic;
    private String email;

    private String depart_name;

    private Integer attendState1;
    private Integer attendState2;
    private Integer attendState3;
    private Integer attendState4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(int depart_id) {
        this.depart_id = depart_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getBan() {
        return ban;
    }

    public void setBan(String ban) {
        this.ban = ban;
    }

    public String getSchoolno() {
        return schoolno;
    }

    public void setSchoolno(String schoolno) {
        this.schoolno = schoolno;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    public Integer getAttendState1() {
        return attendState1;
    }

    public void setAttendState1(Integer attendState1) {
        this.attendState1 = attendState1;
    }

    public Integer getAttendState2() {
        return attendState2;
    }

    public void setAttendState2(Integer attendState2) {
        this.attendState2 = attendState2;
    }

    public Integer getAttendState3() {
        return attendState3;
    }

    public void setAttendState3(Integer attendState3) {
        this.attendState3 = attendState3;
    }

    public Integer getAttendState4() {
        return attendState4;
    }

    public void setAttendState4(Integer attendState4) {
        this.attendState4 = attendState4;
    }
}
