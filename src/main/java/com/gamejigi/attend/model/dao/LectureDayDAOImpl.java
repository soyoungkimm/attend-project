package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.NoticeDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LectureDayDAOImpl extends DAOImplMySQL implements LectureDayDAO{

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public LectureDayDAOImpl() {
        conn = getConnection();
    }

    @Override
    public List<LectureDayDTO> readList(String date, int teacher_id) {
        ArrayList<LectureDayDTO> lectureDayList = null;
        String sql = "select lectureday.*, lecture.class, subject.name as subject_name, " +
                "depart.name as depart_name, room.name as room_name, teacher.name as teacher_name, " +
                "(select count(*) as student_num from mylecture " +
                "where mylecture.lecture_id = lecture.id) as student_num " +
                "from lectureday " +
                "join lecture on lectureday.lecture_id=lecture.id " +
                "join subject on lecture.subject_id=subject.id " +
                "join depart on subject.depart_id=depart.id " +
                "join room on lectureday.room_id=room.id " +
                "join teacher on lecture.teacher_id=teacher.id " +
                "where IF(normstate = 3, lectureday.restdate=?, lectureday.normdate=?) and lecture.teacher_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setString(2, date);
            pstmt.setInt(3, teacher_id);
            rs = pstmt.executeQuery();
            lectureDayList = new ArrayList<LectureDayDTO>();
            while(rs.next()) {
                LectureDayDTO lectureDayDTO = new LectureDayDTO();
                lectureDayDTO.setId(rs.getLong("id"));
                lectureDayDTO.setNormdate(rs.getString("normdate"));
                lectureDayDTO.setNormstart(rs.getInt("normstart"));
                lectureDayDTO.setNormhour(rs.getInt("normhour"));
                lectureDayDTO.setNormstate(rs.getInt("normstate"));
                lectureDayDTO.setRestdate(rs.getString("restdate"));
                lectureDayDTO.setReststart(rs.getInt("reststart"));
                lectureDayDTO.setResthour(rs.getInt("resthour"));
                lectureDayDTO.setBan(rs.getString("class"));
                lectureDayDTO.setSubjectName(rs.getString("subject_name"));
                lectureDayDTO.setDepartName(rs.getString("depart_name"));
                lectureDayDTO.setRoomName(rs.getString("room_name"));
                lectureDayDTO.setTeacherName(rs.getString("teacher_name"));
                lectureDayDTO.setStudentNum(rs.getInt("student_num"));
                lectureDayList.add(lectureDayDTO);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lectureDayList;
    }

    @Override
    public LectureDayDTO findById(Long id) {
        LectureDayDTO lectureDayDTO = new LectureDayDTO();
        String sql = "select lectureday.*, lecture.class, subject.name as subject_name, " +
                "depart.name as depart_name, room.name as room_name, teacher.name as teacher_name, " +
                "building.name as building_name, lectureday.starth " +
                "from lectureday " +
                "join lecture on lectureday.lecture_id=lecture.id " +
                "join subject on lecture.subject_id=subject.id " +
                "join depart on subject.depart_id=depart.id " +
                "join room on lectureday.room_id=room.id " +
                "join teacher on lecture.teacher_id=teacher.id " +
                "join building on room.building_id=building.id " +
                "where lectureday.id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                lectureDayDTO.setId(rs.getLong("id"));
                lectureDayDTO.setLectureId(rs.getInt("lecture_id"));
                lectureDayDTO.setNormdate(rs.getString("normdate"));
                lectureDayDTO.setNormstart(rs.getInt("normstart"));
                lectureDayDTO.setNormhour(rs.getInt("normhour"));
                lectureDayDTO.setNormstate(rs.getInt("normstate"));
                lectureDayDTO.setRestdate(rs.getString("restdate"));
                lectureDayDTO.setReststart(rs.getInt("reststart"));
                lectureDayDTO.setResthour(rs.getInt("resthour"));
                lectureDayDTO.setBan(rs.getString("class"));
                lectureDayDTO.setSubjectName(rs.getString("subject_name"));
                lectureDayDTO.setDepartName(rs.getString("depart_name"));
                lectureDayDTO.setRoomName(rs.getString("room_name"));
                lectureDayDTO.setTeacherName(rs.getString("teacher_name"));
                lectureDayDTO.setBuildingName(rs.getString("building_name"));
                lectureDayDTO.setStarth(rs.getInt("starth"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lectureDayDTO;
    }

    @Override
    public int[] findStarthAndNormOrResthour(Long id) {
        int[] result = new int[2];

        String sql = "select starth, IF(normstate=3, resthour, normhour) as hour from lectureday where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                result[0] = rs.getInt("starth");
                result[1] = rs.getInt("hour");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public int findLectureIdById(int lectureday_id) {
        int result = 0;
        String sql = "select lecture_id from lectureday where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, lectureday_id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public LectureDayDTO findNormstartAndNormhourAndNormdate(int lecture_id) {
        LectureDayDTO lectureDayDTO = new LectureDayDTO();
        String sql = "select DISTINCT min(normdate) as startdate, normstart, normhour from lectureday\n" +
                "where lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lectureDayDTO.setNormstart(rs.getInt("normstart"));
                lectureDayDTO.setNormhour(rs.getInt("normhour"));
                lectureDayDTO.setNormdate(rs.getString("startdate"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return lectureDayDTO;
    }

    @Override
    public int findHourByLectureId(int lecture_id) {
        int hour = 0;
        String sql = "select DISTINCT normhour from lectureday " +
                "where lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                hour = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return hour;
    }

    @Override
    public List<LectureDayDTO> readRestList(int lecture_id) {
        List<LectureDayDTO> restList = new ArrayList<>();
        String sql = "select restdate, starth from lectureday where normstate=3 and lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, lecture_id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                LectureDayDTO lectureDayDTO = new LectureDayDTO();
                lectureDayDTO.setRestdate(rs.getString("restdate"));
                lectureDayDTO.setStarth(rs.getInt("starth"));
                restList.add(lectureDayDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return restList;
    }

    @Override
    public List<LectureDayDTO> findRestStarthAndHour(int lecture_id) {
        List<LectureDayDTO> restList = new ArrayList<>();
        String sql = "select normhour, starth from lectureday where normstate=3 and lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, lecture_id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                LectureDayDTO lectureDayDTO = new LectureDayDTO();
                lectureDayDTO.setNormhour(rs.getInt("normhour"));
                lectureDayDTO.setStarth(rs.getInt("starth"));
                restList.add(lectureDayDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return restList;
    }
}
