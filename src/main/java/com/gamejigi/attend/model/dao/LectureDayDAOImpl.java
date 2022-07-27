package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
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

    @Override
    public List<LectureDayDTO> readListUsePaginationByTeacher(Pagination pagination, int id) {
        List<LectureDayDTO> result = null;
        String sql = "select lectureday.*, lecture.class, subject.name as subject_name, " +
                "depart.name as depart_name, room.name as room_name, teacher.name as teacher_name, " +
                "subject.grade as subject_grade " +
                "from lectureday " +
                "join lecture on lectureday.lecture_id=lecture.id " +
                "join subject on lecture.subject_id=subject.id " +
                "join depart on subject.depart_id=depart.id " +
                "join room on lectureday.room_id=room.id " +
                "join teacher on lecture.teacher_id=teacher.id " +
                "where lectureday.state = 0 and lecture.teacher_id=? " +
                "order by lectureday.restdate asc limit ?, ?";

        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());

            if((rs = pstmt.executeQuery()) != null){
                result = new ArrayList<>();
                while(rs.next()){
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
                    lectureDayDTO.setGrade(rs.getString("subject_grade"));
                    result.add(lectureDayDTO);
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public int getTotalRowsByTeacher(int id) {
        int result = 0;
        String sql = "select COUNT(*) " +
                "from lectureday " +
                "join lecture on lectureday.lecture_id=lecture.id "+
                "where lectureday.state = 0 and lecture.teacher_id=?";

        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public int getTotalRows() {
        int result = 0;
        String sql = "select COUNT(*) from lectureday";

        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public void updateForLecMove(LectureDayDTO lectureDay) {
        String sql = "" +
                "update lectureday " +
                "set restdate='"+lectureDay.getRestdate()+"', " +
                "resthour='"+lectureDay.getResthour()+"', " +
                "reststart='"+lectureDay.getReststart()+"', " +
                "reststate='"+lectureDay.getReststate()+"', " +
                "normstate='"+lectureDay.getNormstate()+"', " +
                "room_id='"+lectureDay.getRoomId()+"', " +
                "state='"+lectureDay.getState()+"' " +
                "where normdate LIKE '"+lectureDay.getNormdate()+"' and " +
                "normhour="+lectureDay.getNormhour()+" and " +
                "normstart="+lectureDay.getNormstart();
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }

    @Override
    public List<LectureDayDTO> readRestListUsePaginationAndDepartId(Pagination pagination, int depart_id) {
        List<LectureDayDTO> restList = new ArrayList<>();
        String sql = "select lectureday.id as lectureday_id, lectureday.*, lecture.class, subject.*, room.name as room_name, building.name as building_name, depart.name as depart_name ,teacher.name as teacher_name"+
                " from lectureday left join lecture on lectureday.lecture_id = lecture.id "+
                " left join subject on lecture.subject_id = subject.id "+
                " left join room on lectureday.room_id = room.id "+
                " left join building on room.building_id = building.id "+
                " left join depart on subject.depart_id = depart.id "+
                " left join teacher on lecture.teacher_id = teacher.id"+
                " where (normstate=3 OR reststate=3) and subject.depart_id=?"+
                " order by state asc, restdate asc limit ?, ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, depart_id);
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());
            rs = pstmt.executeQuery();
            while(rs.next()) {
                LectureDayDTO lectureDayDTO = new LectureDayDTO();
                lectureDayDTO.setId(rs.getLong("lectureday_id"));
                lectureDayDTO.setSubjectName(rs.getString("name"));
                lectureDayDTO.setRoomName(rs.getString("room_name"));
                lectureDayDTO.setBuildingName(rs.getString("building_name"));
                lectureDayDTO.setDepartName(rs.getString("depart_name"));
                lectureDayDTO.setTeacherName(rs.getString("teacher_name"));
                lectureDayDTO.setGrade(rs.getString("grade"));
                lectureDayDTO.setBan(rs.getString("class"));
                lectureDayDTO.setNormdate(rs.getString("normdate"));
                lectureDayDTO.setNormstart(rs.getInt("normstart"));
                lectureDayDTO.setNormhour(rs.getInt("normhour"));
                lectureDayDTO.setRestdate(rs.getString("restdate"));
                lectureDayDTO.setReststart(rs.getInt("reststart"));
                lectureDayDTO.setResthour(rs.getInt("resthour"));
                lectureDayDTO.setState(rs.getInt("state"));
                lectureDayDTO.setTerm(rs.getInt("term"));
                restList.add(lectureDayDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return restList;
    }

    @Override
    public int readTotalRowNumUseDepartID(int depart_id){
        int totalNum = 0;
        String query = "select  count(*) as num from lectureday"+
                " left join lecture on lectureday.lecture_id = lecture.id"+
                "  left join subject on lecture.subject_id = subject.id"+
                " where (normstate=3 OR reststate=3) and subject.depart_id=?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, depart_id);

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
    public int updateStateById(Long id, int state){
        int rows = 0;
        String query = "update lectureday set state=? where id=?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, state);
            pstmt.setLong(2, id);

            rows = pstmt.executeUpdate(); // 1이상이면 정상, 0이하면 오류

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;

    }

    @Override
    public List<LectureDayDTO> readListUsePaginationAndYearAndTerm(Pagination pagination, String year, String term) {
        ArrayList<LectureDayDTO> lecMoveList = new ArrayList<>();
        String query = "select lectureday.id as lectureday_id, lectureday.*, lecture.class, subject.*, room.name as room_name, building.name as building_name, depart.name as depart_name ,teacher.name as teacher_name"+
                " from lectureday " +
                " join lecture on lectureday.lecture_id = lecture.id "+
                " join subject on lecture.subject_id = subject.id "+
                " join room on lectureday.room_id = room.id "+
                " join building on room.building_id = building.id "+
                " join depart on subject.depart_id = depart.id "+
                " join teacher on lecture.teacher_id = teacher.id"+
                " where (normstate=3 OR reststate=3) and state = 2 and subject.yyyy = ? and subject.term = ? " +
                " order by restdate asc limit ?, ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,Integer.parseInt(year));
            pstmt.setInt(2,Integer.parseInt(term));
            pstmt.setInt(3, pagination.getFirstRow() - 1);
            pstmt.setInt(4, pagination.getPerPageRows());
            rs = pstmt.executeQuery();
            while(rs.next()) {
                LectureDayDTO lectureDayDTO = new LectureDayDTO();
                lectureDayDTO.setId(rs.getLong("lectureday_id"));
                lectureDayDTO.setSubjectName(rs.getString("name"));
                lectureDayDTO.setRoomName(rs.getString("room_name"));
                lectureDayDTO.setBuildingName(rs.getString("building_name"));
                lectureDayDTO.setDepartName(rs.getString("depart_name"));
                lectureDayDTO.setTeacherName(rs.getString("teacher_name"));
                lectureDayDTO.setGrade(rs.getString("grade"));
                lectureDayDTO.setBan(rs.getString("class"));
                lectureDayDTO.setNormdate(rs.getString("normdate"));
                lectureDayDTO.setNormstart(rs.getInt("normstart"));
                lectureDayDTO.setNormhour(rs.getInt("normhour"));
                lectureDayDTO.setRestdate(rs.getString("restdate"));
                lectureDayDTO.setReststart(rs.getInt("reststart"));
                lectureDayDTO.setResthour(rs.getInt("resthour"));
                lectureDayDTO.setState(rs.getInt("state"));
                lecMoveList.add(lectureDayDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return lecMoveList;
    }

    @Override
    public int readTotalNumUseYearAndTerm(String year, String term) {
        int totalNum = 0;
        String query = "select  count(*) as num from lectureday"+
                " join lecture on lectureday.lecture_id = lecture.id"+
                " join subject on lecture.subject_id = subject.id"+
                " where (normstate=3 OR reststate=3) and state=2 and subject.yyyy = ? and subject.term = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1,year);
            pstmt.setString(2,term);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                totalNum = rs.getInt("num");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return totalNum;
    }
}
