package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.NoticeDTO;
import com.gamejigi.attend.model.dto.StudentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyLectureDAOImpl extends DAOImplMySQL implements MyLectureDAO{

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
}
