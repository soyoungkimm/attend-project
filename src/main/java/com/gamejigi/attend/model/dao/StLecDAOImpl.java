package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.StLecDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StLecDAOImpl extends DAOImplMySQL implements StLecDAO  {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public StLecDAOImpl() {
        conn = getConnection();
    }

    @Override
    public List<StLecDTO> readList(int term) {
        ArrayList<StLecDTO> stLecList  = null;
        String sql = "select subject.*, lecture.id as lecture_id, lecture.teacher_id, lecture.class" +
                    ", depart.name as depart_name, teacher.name as teacher_name " +
                    "from subject, lecture, depart, teacher " +
                    "WHERE subject.id=lecture.subject_id AND subject.depart_id=depart.id AND lecture.teacher_id=teacher.id AND subject.term=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, term);
            if((rs = pstmt.executeQuery()) != null) {
                stLecList = new ArrayList<StLecDTO>();
                while (rs.next()) {
                    StLecDTO stLec = new StLecDTO();
                    stLec = setStLec(rs);
                    stLecList.add(stLec);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return stLecList;
    }

    @Override
    public int findDepartIdByDepartName(String depart_name) {
        int depart_id = 0;
        String sql = "select id from depart where name=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, depart_name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                depart_id = rs.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return depart_id;
    }

    @Override
    public int findTermByLectureId(int lecture_id) {
        int term = 0;
        String sql = "SELECT subject.term FROM subject JOIN lecture on subject.id = lecture.subject_id where lecture.id=?;";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                term = rs.getInt("term");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return term;
    }

    private StLecDTO setStLec(ResultSet rs) throws SQLException {

        StLecDTO stLecDTO = new StLecDTO();
        stLecDTO.setId(rs.getInt(1));
        stLecDTO.setDepart_id(rs.getInt(2));
        stLecDTO.setCode(rs.getString(3));
        stLecDTO.setYyyy(rs.getString(4));
        stLecDTO.setGrade(rs.getInt(5));
        stLecDTO.setTerm(rs.getInt(6));
        stLecDTO.setIsmajor(rs.getInt(7));
        stLecDTO.setIschoice(rs.getInt(8));
        stLecDTO.setIspractice(rs.getInt(9));
        stLecDTO.setSubject_name(rs.getString(10));
        stLecDTO.setPoint(rs.getInt(11));
        stLecDTO.setHour(rs.getInt(12));
        stLecDTO.setLecture_id(rs.getInt(13));
        stLecDTO.setTeacher_id(rs.getInt(14));
        stLecDTO.setClass_name(rs.getString(15));
        stLecDTO.setDepart_name(rs.getString(16));
        stLecDTO.setTeacher_name(rs.getString(17));
        return stLecDTO;
    }
    
}
