package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.TimetableDTO;

import java.sql.*;
import java.util.*;

public class TimetableDAOImpl extends DAOImplMySQL implements TimetableDAO {
    private Connection conn;

    public TimetableDAOImpl() {conn = getConnection();}

    private TimetableDTO resultSetToTimetable(ResultSet rs) throws SQLException {
        TimetableDTO timetable = new TimetableDTO();
        timetable.setId(rs.getInt(1));
        timetable.setLecture_id(rs.getInt(2));
        timetable.setWeekday(rs.getInt(3));
        timetable.setIstart(rs.getInt(4));
        timetable.setIhour(rs.getInt(5));
        timetable.setRoom_id(rs.getInt(6));
        return timetable;
    }

    @Override
    public List<TimetableDTO> readList() {
        List<TimetableDTO> list = new ArrayList<>();
        String query = "select * from Timetable";
        try {
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(query);
            if (rs == null) {
                sm.close();
                return list;
            }
            while (rs.next()) {
                list.add(resultSetToTimetable(rs));
            }
            rs.close();
            sm.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return list;
    }

    @Override
    public List<TimetableDTO> readListUseLecture(int lecture_id) {
        List<TimetableDTO> list = new ArrayList<>();
        String query = "select * from Timetable where lecture_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, lecture_id);
            ResultSet rs = ps.executeQuery();
            if (rs == null) {
                ps.close();
                return list;
            }
            while (rs.next()) {
                list.add(resultSetToTimetable(rs));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return list;
    }

    @Override
    public TimetableDTO readUseId(int id) {
        TimetableDTO result = null;
        String query = "select * from Timetable where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs == null) {
                ps.close();
                return null;
            }
            if (rs.next()) {
                result = resultSetToTimetable(rs);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return result;
    }

    @Override
    public int create(TimetableDTO timetable) {
        String query = "insert into timetable values (?, ?, ?, ?, ?, ?)";
        int row = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, timetable.getId());
            ps.setInt(2, timetable.getLecture_id());
            ps.setInt(3, timetable.getWeekday());
            ps.setInt(4, timetable.getIstart());
            ps.setInt(5, timetable.getIhour());
            ps.setInt(6, timetable.getRoom_id());
            row = ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return row;
    }

    @Override
    public int update(TimetableDTO timetable) {
        String query = "update timetable set lecture_id=?, weekday=?, istart=?, ihour=?, room_id=? where id=?";
        int row = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, timetable.getLecture_id());
            ps.setInt(2, timetable.getWeekday());
            ps.setInt(3, timetable.getIstart());
            ps.setInt(4, timetable.getIhour());
            ps.setInt(5, timetable.getRoom_id());
            ps.setInt(6, timetable.getId());
            row = ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return row;
    }

    @Override
    public int delete(int id) {
        String query = "delete from timetable where id = ?";
        int row = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            row = ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return row;
    }

    @Override
    public int count() {
        String query = "select count(*) from timetable";
        int count = 0;
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            if (rs != null && rs.next()) {
                count = rs.getInt(1);
                rs.close();
            }
            s.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return count;
    }

    @Override
    public int getMaxId() {
        String query = "select id from timetable order by id desc limit 0, 1";
        int maxId = 0;
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            if (rs != null && rs.next()) {
                maxId = rs.getInt(1);
                rs.close();
            }
            s.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return maxId;
    }
}
