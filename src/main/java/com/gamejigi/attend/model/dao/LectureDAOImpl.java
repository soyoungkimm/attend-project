package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LectureDTO;
import com.gamejigi.attend.model.dto.SubjectDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LectureDAOImpl extends DAOImplMySQL implements LectureDAO{

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public LectureDAOImpl(){
        conn = getConnection();
    }

    @Override
    public int create(LectureDTO lectureDTO) {
        String query = "insert into lecture(subject_id,teacher_id,class) values(?,?,?)";
        int id = 0;
        try{
            pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, lectureDTO.getSubject_id());
            pstmt.setInt(2, lectureDTO.getTeacher_id());
            pstmt.setString(3, lectureDTO.getClasss());
            if(pstmt.executeUpdate() > 0){ //정상: 1이상, 0이하: 에러
                rs = pstmt.getGeneratedKeys();
                while(rs.next()){
                    id = rs.getInt(1);
                }

            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return id;
    }

    @Override
    public LectureDTO read(LectureDTO lectureDTO) {
        LectureDTO retLecture = null;
        String query = "select * from lecture where id=?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, lectureDTO.getId());

            rs = pstmt.executeQuery();

            if(rs.next()) {
                retLecture = new LectureDTO();
                retLecture.setId(rs.getInt("id"));
                retLecture.setSubject_id(rs.getInt("yyyy"));
                retLecture.setTeacher_id(rs.getInt("holiday"));
                retLecture.setClasss(rs.getString("reason"));
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return retLecture;
    }

    @Override
    public LectureDTO findById(int id) {
        LectureDTO retlecture = null;
        String query = "select lecture.*, subject.name as subject_name, teacher.name as teacher_name  from lecture left join subject on lecture.subject_id=subject.id left join teacher on lecture.teacher_id=teacher.id where lecture.id=?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if(rs.next()) { // rs.next()는 반환된 객체에 속한 요소가 있는지를 반환하고, 다름 요소로 접근
                retlecture = new LectureDTO();
                retlecture.setId(rs.getInt("id"));
                retlecture.setSubject_id(rs.getInt("lecture.id"));
                retlecture.setSubject_id(rs.getInt("subject_id"));
                retlecture.setTeacher_id(rs.getInt("teacher_id"));
                retlecture.setName(rs.getString("subject_name"));
                retlecture.setClasss(rs.getString("class"));
                retlecture.setTeacher_name(rs.getString("teacher_name"));

                //retlecture = setLectureDTO(rs);

            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return retlecture;
    }

    @Override
    public List<LectureDTO> readListUsePaginationAndSearch(Pagination pagination, int depart_id, String yyyy, String term, String grade) {

        ArrayList<LectureDTO> lectureList = null;
        String query = "select lecture.id as lecture_id, lecture.*, subject.* from lecture left join subject on subject.id = lecture.subject_id where depart_id=? and yyyy=? and term=? and grade=? limit ?, ?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, depart_id);
            pstmt.setString(2, yyyy);
            pstmt.setString(3, term);
            pstmt.setString(4, grade);
            pstmt.setInt(5, pagination.getFirstRow() - 1);
            pstmt.setInt(6, pagination.getPerPageRows());

            if((rs = pstmt.executeQuery()) != null){
                lectureList = new ArrayList<LectureDTO>();
                while(rs.next()){
                    LectureDTO lectureDTO = new LectureDTO();
                    lectureDTO = setLectureDTO(rs);
                    lectureList.add(lectureDTO);
                }
            }
        }catch (SQLException throwables){
            System.out.println("exception222");

            throwables.printStackTrace();

        }
        return lectureList;
    }

    @Override
    public int update(LectureDTO lectureDTO) {
        conn = this.getConnection();
        int id = 0;
        String query = "update lecture set subject_id=?, teacher_id=?, class=? where id=?";
        try{
                pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, lectureDTO.getSubject_id());
                pstmt.setInt(2, lectureDTO.getTeacher_id());
                pstmt.setString(3, lectureDTO.getClasss());
                pstmt.setInt(4, lectureDTO.getId());

                if(pstmt.executeUpdate() > 0){ //정상: 1이상, 0이하: 에러
                    rs = pstmt.getGeneratedKeys();
                    while(rs.next()){
                        id = rs.getInt(1);
                    }
                }

        }catch (SQLException throwables){
            throwables.printStackTrace();

        }finally{
            this.closeResources(conn, stmt, pstmt, rs);

        }

        return id;
    }

    @Override
    public int delete(int id) {
        conn = this.getConnection();
        int rows = 0;
        String query = "delete from lecture where id=?";
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            rows = pstmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            this.closeResources(conn, stmt, pstmt, rs);

        }

        return rows;
    }


    @Override
    public int readTotalRowNumUseSearch(int depart_id, String yyyy, String term, String grade) {

        int totalNum = 0;
        String query = "select count(*) as num from lecture left join subject on subject.id = lecture.subject_id where subject.depart_id=? and subject.yyyy=? and subject.term=? and subject.grade=? ";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, depart_id);
            pstmt.setString(2, yyyy);
            pstmt.setString(3, term);
            pstmt.setString(4, grade);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                totalNum = rs.getInt("num");
            }
        } catch (SQLException throwables) {

            throwables.printStackTrace();
        }
        return totalNum;
    }


    private LectureDTO setLectureDTO(ResultSet rs) throws SQLException {

        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setId(rs.getInt("lecture_id"));
        lectureDTO.setSubject_id(rs.getInt("subject_id"));
        lectureDTO.setTeacher_id(rs.getInt("teacher_id"));
        lectureDTO.setClasss(rs.getString("class"));
        lectureDTO.setCode(rs.getString("code"));
        lectureDTO.setHour(rs.getString("hour"));
        lectureDTO.setPoint(rs.getString("point"));
        lectureDTO.setName(rs.getString("name"));
        lectureDTO.setDepart_id(rs.getInt("depart_id"));

        return lectureDTO;
    }


    @Override
    public List<SubjectDTO> findSubjectByTeacherId(int teacher_id) {
        List<SubjectDTO> subjectList = new ArrayList<>();
        String query = "select distinct subject.id, subject.name " +
                "from lecture " +
                "join subject on lecture.subject_id=subject.id " +
                "join teacher on lecture.teacher_id=teacher.id " +
                "where lecture.teacher_id=?";
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, teacher_id);
            if((rs = pstmt.executeQuery()) != null){
                while(rs.next()){
                    SubjectDTO subjectDTO = new SubjectDTO();
                    subjectDTO.setName(rs.getString("name"));
                    subjectDTO.setId(rs.getInt("id"));
                    subjectList.add(subjectDTO);
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return subjectList;
    }


    @Override
    public int findIdByTeacherIdAndSearchText(String year, int term, int grade, String ban, int subject, int teacher_id) {
        int result = 0;
        String sql = "select lecture.id from lecture " +
                "join subject on lecture.subject_id=subject.id " +
                "where subject.id=? and " +
                "subject.term=? and " +
                "subject.grade=? and " +
                "subject.yyyy=? and " +
                "lecture.class=? and " +
                "lecture.teacher_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, subject);
            pstmt.setInt(2, term);
            pstmt.setInt(3, grade);
            pstmt.setString(4, year);
            pstmt.setString(5, ban);
            pstmt.setInt(6, teacher_id);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
