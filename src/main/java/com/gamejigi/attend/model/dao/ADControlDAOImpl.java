package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.ADControlDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ADControlDAOImpl extends DAOImplMySQL implements ADControlDAO {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public ADControlDAOImpl() {
        conn = getConnection();
    }

    @Override
    public ADControlDTO read() {
        ADControlDTO adControlDTO = new ADControlDTO();
        String sql = "select * from attendcontrol limit 1";

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                adControlDTO = setADControl(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return adControlDTO;
    }

    @Override
    public int readTotalRowNum() {
        int totalNum = 0;
        String sql = "select count(*) as num from attendcontrol";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                totalNum = rs.getInt("num");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return totalNum;
    }

    @Override
    public int create() {
        int rows = 0;
        String sql = "insert into attendcontrol(subjecttime, lecturetime, mylecturetime, attendtime) values(0, 0, 0, 0)";
        try {
            pstmt = conn.prepareStatement(sql);
            rows = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int update(ADControlDTO adControlDTO) {
        int rows = 0;
        String sql = "update attendcontrol set subjecttime=?, lecturetime=?, mylecturetime=?, attendtime=? where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, adControlDTO.getSubjecttime());
            pstmt.setInt(2, adControlDTO.getLecturetime());
            pstmt.setInt(3, adControlDTO.getMylecturetime());
            pstmt.setInt(4, adControlDTO.getAttendtime());
            pstmt.setInt(5, adControlDTO.getId());
            rows = pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    private ADControlDTO setADControl(ResultSet rs) throws SQLException {
        ADControlDTO adControlDTO = new ADControlDTO();
        adControlDTO.setId(rs.getInt(1));
        adControlDTO.setSubjecttime(rs.getInt(2));
        adControlDTO.setLecturetime(rs.getInt(3));
        adControlDTO.setMylecturetime(rs.getInt(4));
        adControlDTO.setAttendtime(rs.getInt(5));
        return adControlDTO;
    }
}
