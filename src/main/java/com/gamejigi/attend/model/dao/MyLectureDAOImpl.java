package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.MylectureDTO;
import com.gamejigi.attend.model.dto.NoticeDTO;
import com.gamejigi.attend.model.dto.StLecDTO;
import com.gamejigi.attend.model.dto.StudentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        else sql += "h" + starth + "=?, h" + (starth + 1) + "=?, h" + (starth + 2) + "=?, h" + (starth + 3) + "=?";
        sql += " where lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            if (normhour == 1) {
                pstmt.setInt(1, 0);
                pstmt.setInt(2, lecture_id);
            } else if (normhour == 2) {
                pstmt.setInt(1, 0);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, lecture_id);
            } else if (normhour == 3) {
                pstmt.setInt(1, 0);
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 0);
                pstmt.setInt(4, lecture_id);
            } else {
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
        String sql = "insert into mylecture(student_id, lecture_id, departname, grade, term, retake) values(?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stLecDTO.getStudent_id());
            pstmt.setInt(2, stLecDTO.getLecture_id());
            pstmt.setString(3, stLecDTO.getDepart_name());
            pstmt.setInt(4, stLecDTO.getGrade());
            pstmt.setInt(5, stLecDTO.getTerm());
            pstmt.setInt(6, stLecDTO.getRetake());
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


}
