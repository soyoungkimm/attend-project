package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StLecQaDAOImpl extends DAOImplMySQL implements StLecQaDAO {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public StLecQaDAOImpl() {
        conn= getConnection();
    }

    @Override
    public List<MylectureDTO> readListUsePaginationByStId(Pagination pagination, int student_id) {
        ArrayList<MylectureDTO> mylectureList = null;
        String query = "SELECT mylecture.*, subject.name as subject_name, teacher.name as teacher_name " +
                "FROM mylecture, subject, teacher, lecture " +
                "WHERE mylecture.lecture_id=lecture.id AND subject.id = lecture.subject_id AND teacher.id = lecture.teacher_id " +
                "AND  (mylecture.qkind=1 OR mylecture.qkind=2) AND mylecture.student_id=? " +
                "ORDER BY mylecture.qday asc limit ?, ?;";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, student_id);
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());
            rs = pstmt.executeQuery();
            mylectureList = new ArrayList<>();
            while (rs.next()) {
                MylectureDTO mylectureDTO = new MylectureDTO();
                mylectureDTO.setId(rs.getInt("id"));
                mylectureDTO.setQkind(rs.getInt("qkind"));
                mylectureDTO.setQday(rs.getString("qday"));
                mylectureDTO.setQtitle(rs.getString("qtitle"));
                mylectureDTO.setQask(rs.getString("qask"));
                mylectureDTO.setQanswer(rs.getString("qanswer"));
                mylectureDTO.setSubject_name(rs.getString("subject_name"));
                mylectureDTO.setTeacher_name(rs.getString("teacher_name"));
                mylectureList.add(mylectureDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return mylectureList;
    }

    @Override
    public int readTotalNumByStId(int student_id) {
        int totalNum = 0;
        String query = "SELECT COUNT(*) as num FROM mylecture, subject, teacher, lecture " +
                "WHERE mylecture.lecture_id=lecture.id AND subject.id = lecture.subject_id " +
                "AND teacher.id = lecture.teacher_id AND  (mylecture.qkind=1 OR mylecture.qkind=2) " +
                "AND mylecture.student_id=?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, student_id);
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
    public MylectureDTO readLecQaByStIdAndMylecId(int mylecture_id , int student_id) {
        MylectureDTO mylectureDTO = new MylectureDTO();
        String query = "SELECT mylecture.*, subject.name as subject_name, teacher.name as teacher_name, lecture.class as lecture_class " +
                "FROM mylecture, subject, teacher, lecture " +
                "WHERE mylecture.lecture_id=lecture.id AND subject.id = lecture.subject_id AND teacher.id = lecture.teacher_id " +
                "AND  (mylecture.qkind=1 OR mylecture.qkind=2) " +
                "AND mylecture.student_id=? AND mylecture.id = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, student_id);
            pstmt.setInt(2, mylecture_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mylectureDTO.setId(rs.getInt("id"));
                mylectureDTO.setQkind(rs.getInt("qkind"));
                mylectureDTO.setQday(rs.getString("qday"));
                mylectureDTO.setQtitle(rs.getString("qtitle"));
                mylectureDTO.setQask(rs.getString("qask"));
                mylectureDTO.setQanswer(rs.getString("qanswer"));
                mylectureDTO.setSubject_name(rs.getString("subject_name"));
                mylectureDTO.setTeacher_name(rs.getString("teacher_name"));
                mylectureDTO.setDepart_name(rs.getString("departname"));
                mylectureDTO.setGrade(rs.getInt("grade"));
                mylectureDTO.setClass_name(rs.getString("lecture_class"));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return mylectureDTO;
    }

    @Override
    public int create(MylectureDTO mylectureDTO) {
        int rows = 0;
        String sql = "update mylecture set qkind = ?, qday = ?, qtitle = ?, qask = ? where student_id = ? and lecture_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, mylectureDTO.getQkind());
            pstmt.setString(2, mylectureDTO.getQday());
            pstmt.setString(3, mylectureDTO.getQtitle());
            pstmt.setString(4, mylectureDTO.getQask());
            pstmt.setInt(5,mylectureDTO.getStudent_id());
            pstmt.setInt(6,mylectureDTO.getLecture_id());
            rows = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int update(MylectureDTO mylectureDTO) {
        int rows = 0;
        String query = "update mylecture set qtitle = ?, qask =? where id = ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, mylectureDTO.getQtitle());
            pstmt.setString(2,mylectureDTO.getQask());
            pstmt.setInt(3,mylectureDTO.getId());

            rows = pstmt.executeUpdate(); // 1이상이면 정상, 0이하면 오류

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }
}
