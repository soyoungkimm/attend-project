package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.TimeTeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeTeacherDAOImpl extends DAOImplMySQL implements TimeTeacherDAO {
    private Connection conn;

    public TimeTeacherDAOImpl() {conn = getConnection();}

    @Override
    public List<Integer> getYearList(int depart) {
        String query = "select distinct s.yyyy from subject as s order by 1 desc";
        List<Integer> result = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getInt(1));
                }
                rs.close();
            }
            s.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return result;
    }

    @Override
    public List<Integer> readIdListUsePagination(Pagination p, int depart) {
        String query = "select t.id from teacher as t " +
                (depart <= 0 ? "" : "where t.depart_id = " + depart) + " " +
                "order by t.name asc " +
                "limit " + (p.getFirstRow()-1) + ", " + p.getPerPageRows();
        List<Integer> result = new ArrayList<>();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            if (rs != null) {
                while (rs.next()) {
                    result.add(rs.getInt(1));
                }
                rs.close();
            }
            s.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return result;
    }

    @Override
    public TimeTeacherDTO getTimeTeacherById(int id, int year, int term) {
        String query = "" +
                "select " +
                "   d.name as departName, " +
                "   t.name as teacherName, " +
                "   t.kind as teacherKind, " +
                "   count(s.id) as subjectCount, " +
                "   sum(s.hour) as weekTeachHour, " +
                "   count(distinct ti.weekday) as weekTeachDay " +
                "from teacher as t " +
                "left join lecture as l on l.teacher_id = t.id " +
                "left join subject as s on s.id = l.subject_id " +
                "left join depart as d on d.id = t.depart_id " +
                "left join timetable as ti on ti.lecture_id = l.id " +
                "where s.yyyy = "+year+" and s.term = "+term+" and t.id = "+id;
        TimeTeacherDTO result = new TimeTeacherDTO();
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            if (rs != null && rs.next()) {
                result.setDepartName(rs.getString(1));
                result.setTeacherName(rs.getString(2));
                result.setTeacherKind(rs.getInt(3));
                result.setSubjectCount(rs.getInt(4));
                result.setWeekTeachHour(rs.getInt(5));
                result.setWeekTeachDay(rs.getInt(6));
                rs.close();
            }
            s.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return result;
    }

    @Override
    public int count(int depart) {
        String query = "select count(*) from teacher as t " +
                (depart <= 0 ? "" : "where t.depart_id = " + depart);
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
}
