package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.MylectureDTO;

import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeLecQaDAOImpl extends DAOImplMySQL implements TeLecQaDAO{

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public TeLecQaDAOImpl(){conn= getConnection();}

    @Override
    public List<MylectureDTO> readListUsePagination(Pagination pagination) {
        ArrayList<MylectureDTO> mylectureList = null;
        String query = "select  mylecture.id as mylecture_id, mylecture.*, " +
                "subject.name as subject_name, " +
                "student.name as student_name"+
                " from mylecture " +
                " join lecture on mylecture.lecture_id = lecture.id "+
                " join subject on lecture.subject_id = subject.id "+
                " join teacher on lecture.teacher_id = teacher.id"+
                " join student on mylecture.lecture_id = student.id"+
                " where qkind = 1 or qkind = 2 " +
                " order by qday asc limit ?, ?";;
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pagination.getFirstRow() - 1);
            pstmt.setInt(2, pagination.getPerPageRows());
            rs = pstmt.executeQuery();
            mylectureList = new ArrayList<>();
            while (rs.next()) {
                MylectureDTO mylectureDTO = new MylectureDTO();
                mylectureDTO.setId(rs.getInt("mylecture_id"));
                mylectureDTO.setQkind(rs.getInt("qkind"));
                mylectureDTO.setQday(rs.getString("qday"));
                mylectureDTO.setQtitle(rs.getString("qtitle"));
                mylectureDTO.setQask(rs.getString("qask"));
                mylectureDTO.setQanswer(rs.getString("qanswer"));
                mylectureDTO.setSubject_name(rs.getString("subject_name"));
                mylectureDTO.setStudent_name(rs.getString("student_name"));
                mylectureList.add(mylectureDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return mylectureList;
    }

    @Override
    public int update(MylectureDTO mylectureDTO) {
        int rows = 0;
        String query = "update mylecture set qkind = ?, qanswer =? where id = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, mylectureDTO.getQkind());
            pstmt.setString(2,mylectureDTO.getQanswer());
            pstmt.setInt(3,mylectureDTO.getId());

            rows = pstmt.executeUpdate(); // 1이상이면 정상, 0이하면 오류

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int readTotalNum() {
        int totalNum = 0;
        String query = "select count(*) as num"+
                " from mylecture " +
                " join lecture on mylecture.lecture_id = lecture.id "+
                " join subject on lecture.subject_id = subject.id "+
                " join teacher on lecture.teacher_id = teacher.id"+
                " join student on mylecture.lecture_id = student.id"+
                " where qkind = 1 or qkind = 2";
        try {
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                totalNum = rs.getInt("num");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return totalNum;
    }

    @Override
    public MylectureDTO readLecQaUseId(MylectureDTO mylectureDTO) {
        MylectureDTO mylecture = null;
        String query = "select mylecture.id as mylecture_id, mylecture.*, " +
                "lecture.class as lecture_class, "+
                "subject.name as subject_name, " +
                "teacher.name as teacher_name, "+
                "student.name as student_name, student.schoolno as student_schoolno, student.phone as student_phone " +
                "from mylecture " +
                "join lecture on mylecture.lecture_id = lecture.id "+
                "join subject on lecture.subject_id = subject.id "+
                "join teacher on lecture.teacher_id = teacher.id "+
                "join student on mylecture.lecture_id = student.id "+
                "where mylecture.id = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, mylectureDTO.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mylecture = new MylectureDTO();

                mylecture.setId(rs.getInt("mylecture_id"));
                mylecture.setQkind(rs.getInt("qkind"));
                mylecture.setQday(rs.getString("qday"));
                mylecture.setQtitle(rs.getString("qtitle"));
                mylecture.setQask(rs.getString("qask"));
                mylecture.setQanswer(rs.getString("qanswer"));

                mylecture.setSubject_name(rs.getString("subject_name"));

                mylecture.setTeacher_name(rs.getString("teacher_name"));

                mylecture.setDepart_name(rs.getString("departname"));
                mylecture.setGrade(rs.getInt("grade"));
                mylecture.setClass_name(rs.getString("lecture_class"));
                mylecture.setStudent_schoolno(rs.getString("student_schoolno"));
                mylecture.setStudent_name(rs.getString("student_name"));
                mylecture.setStudent_phone(rs.getString("student_phone"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return mylecture;
    }
}
