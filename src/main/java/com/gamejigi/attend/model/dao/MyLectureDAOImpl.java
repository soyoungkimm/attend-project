package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.*;
import com.gamejigi.attend.model.dto.MyLectureDTO2;
import com.gamejigi.attend.model.dto.SubjectAttendDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyLectureDAOImpl extends DAOImplMySQL implements MyLectureDAO {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public MyLectureDAOImpl() {
        conn = getConnection();
    }

    @Override
    public int updateAttend(int lecture_id, int student_id, int h, int v) {
        int row_num = 0;

        String sql = "update mylecture set h" + h + "=? where student_id=? and lecture_id=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, v);
            pstmt.setInt(2, student_id);
            pstmt.setInt(3, lecture_id);
            row_num = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return row_num;
    }

    @Override
    public int updateAllAttend(int lecture_id, int starth, int normhour) {
        int row_num = 0;

        String sql = "update mylecture set ";
        if (normhour == 1) sql += "h" + starth + "=?";
        else if (normhour == 2) sql += "h" + starth + "=?, h" + (starth + 1) + "=?";
        else if (normhour == 3) sql += "h" + starth + "=?, h" + (starth + 1) + "=?, h" + (starth + 2) + "=?";
        else  sql += "h" + starth + "=?, h" + (starth + 1) + "=?, h" + (starth + 2) + "=?, h" + (starth + 3) + "=?";
        sql += " where lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            if (normhour == 1) {
                pstmt.setInt(1, 0);
                pstmt.setInt(2, lecture_id);
            }
            else if (normhour == 2) {
                pstmt.setInt(1, 0);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, lecture_id);
            }
            else if (normhour == 3) {
                pstmt.setInt(1, 0);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setInt(4, lecture_id);
            }
            else {
                pstmt.setInt(1, 0);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setInt(4, 0);
                pstmt.setInt(5, lecture_id);
            }
            row_num = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return row_num;
    }



    @Override
    public int createMyLecture(StLecDTO stLecDTO) {
        int rows = 0;
        String sql = "insert into mylecture(student_id, lecture_id, departname, grade, term, ipoint, retake) values(?, ?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stLecDTO.getStudent_id());
            pstmt.setInt(2, stLecDTO.getLecture_id());
            pstmt.setString(3, stLecDTO.getDepart_name());
            pstmt.setInt(4, stLecDTO.getGrade());
            pstmt.setInt(5, stLecDTO.getTerm());
            pstmt.setInt(6, stLecDTO.getPoint());
            pstmt.setInt(7, stLecDTO.getRetake());
            rows = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int deleteMyLecture(int student_id, int lecture_id) {
        int rows = 0;
        String sql = "delete from mylecture where student_id=? AND lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student_id);
            pstmt.setInt(2, lecture_id);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int readRowNumByStudentIdAndLectureId(int student_id, int lecture_id) {
        int count = 0;
        String sql = "select count(*) as num from mylecture where student_id=? AND lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student_id);
            pstmt.setInt(2, lecture_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("num");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    public List<MylectureDTO> readStLecListByStudentId(int student_id) {
        ArrayList<MylectureDTO> mylectureList = null;
        String sql = "select mylecture.id, mylecture.student_id, mylecture.lecture_id, mylecture.departname, mylecture.grade, mylecture.retake, " +
                "lecture.class, subject.ismajor, subject.ischoice, subject.code, subject.name as subject_name, subject.point, subject.hour, " +
                "teacher.name as teacher_name FROM mylecture, lecture, subject, teacher WHERE mylecture.lecture_id=lecture.id " +
                "AND lecture.subject_id=subject.id AND lecture.teacher_id=teacher.id AND mylecture.student_id=? ORDER BY mylecture.id";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student_id);

            rs = pstmt.executeQuery();

            mylectureList = new ArrayList<MylectureDTO>();
            while (rs.next()) {
                MylectureDTO mylectureDTO = new MylectureDTO();
                mylectureDTO.setId(rs.getInt("id"));
                mylectureDTO.setStudent_id(rs.getInt("student_id"));
                mylectureDTO.setLecture_id(rs.getInt("lecture_id"));
                mylectureDTO.setDepart_name(rs.getString("departname"));
                mylectureDTO.setGrade(rs.getInt("grade"));
                mylectureDTO.setRetake(rs.getInt("retake"));
                mylectureDTO.setClass_name(rs.getString("class"));
                mylectureDTO.setIsmajor(rs.getInt("ismajor"));
                mylectureDTO.setIschoice(rs.getInt("ischoice"));
                mylectureDTO.setSubject_code(rs.getString("code"));
                mylectureDTO.setSubject_name(rs.getString("subject_name"));
                mylectureDTO.setPoint(rs.getInt("point"));
                mylectureDTO.setHour(rs.getInt("hour"));
                mylectureDTO.setTeacher_name(rs.getString("teacher_name"));
                mylectureList.add(mylectureDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return mylectureList;
    }



    @Override
    public List<SubjectAttendDTO> readStudentAttendList(int lecture_id) {
        ArrayList<SubjectAttendDTO> subjectAttendList = new ArrayList<>();
        String sql = "select mylecture.*, student.* " +
                "from mylecture " +
                "join student on mylecture.student_id = student.id " +
                "join depart on student.depart_id=depart.id " +
                "where mylecture.lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                SubjectAttendDTO subjectAttendDTO = new SubjectAttendDTO();
                subjectAttendDTO = setSubjectAttend(rs);
                subjectAttendList.add(subjectAttendDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectAttendList;
    }


    @Override
    public List<MyLectureDTO2> readMyLectureList(int lecture_id) {
        ArrayList<MyLectureDTO2> myLectureList = new ArrayList<>();
        String sql = "select * from mylecture where lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MyLectureDTO2 myLectureDTO = new MyLectureDTO2();
                myLectureDTO = setMyLecture(rs);
                myLectureList.add(myLectureDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myLectureList;
    }


    @Override
    public MyLectureDTO2 findByLectureIdAndStudentId(int lecture_id, int student_id) {
        MyLectureDTO2 myLectureDTO = new MyLectureDTO2();
        String sql = "select * from mylecture where lecture_id=? and student_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);
            pstmt.setInt(2, student_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                myLectureDTO = setMyLecture(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myLectureDTO;
    }

    @Override
    public int updateLateAndAbsentAndAttendScore(MyLectureDTO2 myLectureDTO) {
        int row_num = 0;
        String sql = "update mylecture set ilate=?, ixhour=?, iattend=? where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, myLectureDTO.getIlate());
            pstmt.setInt(2, myLectureDTO.getIxhour());
            pstmt.setInt(3, myLectureDTO.getIattend());
            pstmt.setInt(4, myLectureDTO.getId());
            row_num = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row_num;
    }

    @Override
    public MyLectureDTO2 findLateAbsentScore(int lecture_id, int student_id) {
        MyLectureDTO2 myLectureDTO = new MyLectureDTO2();
        String sql = "select ilate, ixhour, iattend from mylecture where lecture_id=? and student_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);
            pstmt.setInt(2, student_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                myLectureDTO.setIlate(rs.getInt("ilate"));
                myLectureDTO.setIxhour(rs.getInt("ixhour"));
                myLectureDTO.setIattend(rs.getInt("iattend"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myLectureDTO;
    }

    @Override
    public List<StudentAttendDTO> readStudentAttendList2(int student_id, int term) {
        ArrayList<StudentAttendDTO> subjectAttendList = new ArrayList<>();
        String sql = "SELECT DISTINCT mylecture.*, lecture.class, lectureday.normhour, subject.name as subject_name " +
                "FROM mylecture, lecture, lectureday, subject " +
                "WHERE mylecture.lecture_id=lecture.id " +
                "AND lectureday.lecture_id=lecture.id " +
                "AND subject.id=lecture.subject_id " +
                "AND student_id = ? AND mylecture.term = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student_id);
            pstmt.setInt(2, term);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                StudentAttendDTO studentAttendDTO;
                studentAttendDTO = setStudentAttend(rs);
                subjectAttendList.add(studentAttendDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectAttendList;
    }

    public MyLectureDTO2 setMyLecture(ResultSet rs) throws SQLException{
        MyLectureDTO2 myLectureDTO = new MyLectureDTO2();
        myLectureDTO.setId((Integer)rs.getObject("id"));
        myLectureDTO.setH1((Integer)rs.getObject("h1"));
        myLectureDTO.setH2((Integer)rs.getObject("h2"));
        myLectureDTO.setH3((Integer)rs.getObject("h3"));
        myLectureDTO.setH4((Integer)rs.getObject("h4"));
        myLectureDTO.setH5((Integer)rs.getObject("h5"));
        myLectureDTO.setH6((Integer)rs.getObject("h6"));
        myLectureDTO.setH7((Integer)rs.getObject("h7"));
        myLectureDTO.setH8((Integer)rs.getObject("h8"));
        myLectureDTO.setH9((Integer)rs.getObject("h9"));
        myLectureDTO.setH10((Integer)rs.getObject("h10"));
        myLectureDTO.setH11((Integer)rs.getObject("h11"));
        myLectureDTO.setH12((Integer)rs.getObject("h12"));
        myLectureDTO.setH13((Integer)rs.getObject("h13"));
        myLectureDTO.setH14((Integer)rs.getObject("h14"));
        myLectureDTO.setH15((Integer)rs.getObject("h15"));
        myLectureDTO.setH16((Integer)rs.getObject("h16"));
        myLectureDTO.setH17((Integer)rs.getObject("h17"));
        myLectureDTO.setH18((Integer)rs.getObject("h18"));
        myLectureDTO.setH19((Integer)rs.getObject("h19"));
        myLectureDTO.setH20((Integer)rs.getObject("h20"));
        myLectureDTO.setH21((Integer)rs.getObject("h21"));
        myLectureDTO.setH22((Integer)rs.getObject("h22"));
        myLectureDTO.setH23((Integer)rs.getObject("h23"));
        myLectureDTO.setH24((Integer)rs.getObject("h24"));
        myLectureDTO.setH25((Integer)rs.getObject("h25"));
        myLectureDTO.setH26((Integer)rs.getObject("h26"));
        myLectureDTO.setH27((Integer)rs.getObject("h27"));
        myLectureDTO.setH28((Integer)rs.getObject("h28"));
        myLectureDTO.setH29((Integer)rs.getObject("h29"));
        myLectureDTO.setH30((Integer)rs.getObject("h30"));
        myLectureDTO.setH31((Integer)rs.getObject("h31"));
        myLectureDTO.setH32((Integer)rs.getObject("h32"));
        myLectureDTO.setH33((Integer)rs.getObject("h33"));
        myLectureDTO.setH34((Integer)rs.getObject("h34"));
        myLectureDTO.setH35((Integer)rs.getObject("h35"));
        myLectureDTO.setH36((Integer)rs.getObject("h36"));
        myLectureDTO.setH37((Integer)rs.getObject("h37"));
        myLectureDTO.setH38((Integer)rs.getObject("h38"));
        myLectureDTO.setH39((Integer)rs.getObject("h39"));
        myLectureDTO.setH40((Integer)rs.getObject("h40"));
        myLectureDTO.setH41((Integer)rs.getObject("h41"));
        myLectureDTO.setH42((Integer)rs.getObject("h42"));
        myLectureDTO.setH43((Integer)rs.getObject("h43"));
        myLectureDTO.setH44((Integer)rs.getObject("h44"));
        myLectureDTO.setH45((Integer)rs.getObject("h45"));

        return myLectureDTO;
    }


    public SubjectAttendDTO setSubjectAttend(ResultSet rs) throws SQLException{
        SubjectAttendDTO subjectAttendDTO = new SubjectAttendDTO();
        Integer[] h = new Integer[45];

        int count = 23;
        for (int i = 0; i < 45; i++) {
            h[i] = (Integer)rs.getObject(count);
            count++;
        }
        subjectAttendDTO.setH(h);
        subjectAttendDTO.setStudentId(rs.getLong("student_id"));
        subjectAttendDTO.setDepartName(rs.getString("departname"));
        subjectAttendDTO.setGrade(rs.getInt("grade"));
        subjectAttendDTO.setSchoolno(rs.getString("schoolno"));
        subjectAttendDTO.setStudentState(rs.getInt("state"));
        subjectAttendDTO.setName(rs.getString("name"));
        subjectAttendDTO.setScore(rs.getInt("iattend"));
        subjectAttendDTO.setLate(rs.getInt("ilate"));
        subjectAttendDTO.setAbsent(rs.getInt("ixhour"));

        return subjectAttendDTO;
    }

    public StudentAttendDTO setStudentAttend(ResultSet rs) throws SQLException{
        StudentAttendDTO studentAttendDTO = new StudentAttendDTO();
        Integer[] h = new Integer[45];

        int count = 23;
        for (int i = 0; i < 45; i++) {
            h[i] = (Integer)rs.getObject(count);
            count++;
        }
        studentAttendDTO.setH(h);
        studentAttendDTO.setSubject_name(rs.getString("subject_name"));
        studentAttendDTO.setGrade(rs.getInt("grade"));
        studentAttendDTO.setPoint(rs.getInt("ipoint"));
        studentAttendDTO.setClass_name(rs.getString("class"));
        studentAttendDTO.setNormhour(rs.getInt("normhour"));
        studentAttendDTO.setScore(rs.getInt("iattend"));
        studentAttendDTO.setLate(rs.getInt("ilate"));
        studentAttendDTO.setAbsent(rs.getInt("ixhour"));
        studentAttendDTO.setLecture_id(rs.getInt("lecture_id"));

        return studentAttendDTO;
    }

    @Override
    public List<MylectureDTO> readSugangList(Pagination pagination, String grade, String term, int student_id) {
        List<MylectureDTO> myLectureDTOList = new ArrayList<>();
        String sql = "SELECT mylecture.*, subject.*, subject.name as subjectName, student.schoolno, student.name as studentName " +
                " FROM mylecture" +
                " left join lecture on mylecture.lecture_id = lecture.id" +
                " left join subject on lecture.subject_id = subject.id" +
                " left join student on mylecture.student_id = student.id" +
                " WHERE mylecture.grade=? AND mylecture.term=? AND mylecture.student_id=? limit ?, ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, grade);
            pstmt.setString(2, term);
            pstmt.setInt(3, student_id);
            pstmt.setInt(4, pagination.getFirstRow() - 1);
            pstmt.setInt(5, pagination.getPerPageRows());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                MylectureDTO myLectureDTO = new MylectureDTO();
                myLectureDTO.setIschoice(rs.getInt("ischoice"));
                myLectureDTO.setIsmajor(rs.getInt("ismajor"));
                myLectureDTO.setSubject_code(rs.getString("code"));
                myLectureDTO.setSubject_name(rs.getString("subjectName"));
                myLectureDTO.setDepart_name(rs.getString("departname"));
                myLectureDTO.setPoint(rs.getInt("point"));


                myLectureDTO.setIpoint(rs.getString("ipoint"));
                myLectureDTO.setIattend(rs.getInt("iattend"));
                myLectureDTO.setImiddle(rs.getInt("imiddle"));
                myLectureDTO.setIlast(rs.getInt("ilast"));
                myLectureDTO.setInormal(rs.getInt("inormal"));
                myLectureDTO.setIpractice(rs.getInt("ipratice"));
                myLectureDTO.setItotal(rs.getInt("itotal"));
                myLectureDTO.setIgrade(rs.getString("igrade"));
                myLectureDTO.setRetake(rs.getInt("retake"));

                myLectureDTOList.add(myLectureDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myLectureDTOList;

    }

    @Override
    public int readTotalRowNumUseSearch(String grade, String term, int student_id){
        int rownum = 0;
        String sql = "select count(*) as num from mylecture where grade=? and term=? and student_id=? ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, grade);
            pstmt.setString(2, term);
            pstmt.setInt(3, student_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                rownum = rs.getInt("num");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rownum;
    }
    public int getPoint(String grade, String term, int student_id){

        int point = 0;////수강학점

        String sql = "select sum(subject.point) as point from mylecture "+
                " left join lecture on mylecture.lecture_id = lecture.id"+
                " left join subject on lecture.subject_id = subject.id"+
                " where mylecture.grade=? and mylecture.term=? and student_id=? ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, grade);
            pstmt.setString(2, term);
            pstmt.setInt(3, student_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                point = rs.getInt("point");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return point;
    }

    @Override
    public double getIpoint(String grade, String term, int student_id){

        double ipoint = 0;////수강학점

        String sql = "select avg(mylecture.ipoint) as ipoint from mylecture "+
                " left join lecture on mylecture.lecture_id = lecture.id"+
                " left join subject on lecture.subject_id = subject.id"+
                " where mylecture.grade=? and mylecture.term=? and student_id=? ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, grade);
            pstmt.setString(2, term);
            pstmt.setInt(3, student_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                ipoint = rs.getDouble("ipoint");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ipoint;
    }

    public int getYear(String grade ,String term, int student_id){
        int yyyy = 0;

        String sql = "select subject.yyyy from mylecture "+
                " left join lecture on mylecture.lecture_id = lecture.id"+
                " left join subject on lecture.subject_id = subject.id"+
                " where mylecture.grade=? and mylecture.term=? and student_id=? limit 1";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, grade);
            pstmt.setString(2, term);
            pstmt.setInt(3, student_id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                yyyy = rs.getInt("subject.yyyy");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return yyyy;
    }
}
