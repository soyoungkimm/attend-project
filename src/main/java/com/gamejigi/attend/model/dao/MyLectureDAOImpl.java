package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.MyLectureDTO;
import com.gamejigi.attend.model.dto.SubjectAttendDTO;

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

    @Override
    public List<SubjectAttendDTO> readStudentAttendList(int lecture_id) {
        ArrayList<SubjectAttendDTO> subjectAttendList = new ArrayList<>();
        String sql = "select mylecture.*, student.* " +
                "from mylecture " +
                "join student on mylecture.student_id = student.id " +
                "join depart on student.depart_id=depart.id " +
                "where mylecture.lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                SubjectAttendDTO subjectAttendDTO = new SubjectAttendDTO();
                subjectAttendDTO = setSubjectAttend(rs);
                subjectAttendList.add(subjectAttendDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return subjectAttendList;
    }


    @Override
    public List<MyLectureDTO> readMyLectureList(int lecture_id) {
        ArrayList<MyLectureDTO> myLectureList = new ArrayList<>();
        String sql = "select * from mylecture where lecture_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MyLectureDTO myLectureDTO = new MyLectureDTO();
                myLectureDTO = setMyLecture(rs);
                myLectureList.add(myLectureDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myLectureList;
    }


    @Override
    public MyLectureDTO findByLectureIdAndStudentId(int lecture_id, int student_id) {
        MyLectureDTO myLectureDTO = new MyLectureDTO();
        String sql = "select * from mylecture where lecture_id=? and student_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);
            pstmt.setInt(2, student_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                myLectureDTO = setMyLecture(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myLectureDTO;
    }

    @Override
    public int updateLateAndAbsentAndAttendScore(MyLectureDTO myLectureDTO) {
        int row_num = 0;
        String sql = "update mylecture set ilate=?, ixhour=?, iattend=? where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, myLectureDTO.getIlate());
            pstmt.setInt(2, myLectureDTO.getIxhour());
            pstmt.setInt(3, myLectureDTO.getIattend());
            pstmt.setInt(4, myLectureDTO.getId());
            row_num = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row_num;
    }

    @Override
    public MyLectureDTO findLateAbsentScore(int lecture_id, int student_id) {
        MyLectureDTO myLectureDTO = new MyLectureDTO();
        String sql = "select ilate, ixhour, iattend from mylecture where lecture_id=? and student_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, lecture_id);
            pstmt.setInt(2, student_id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                myLectureDTO.setIlate(rs.getInt("ilate"));
                myLectureDTO.setIxhour(rs.getInt("ixhour"));
                myLectureDTO.setIattend(rs.getInt("iattend"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return myLectureDTO;
    }

    public MyLectureDTO setMyLecture(ResultSet rs) throws SQLException{
        MyLectureDTO myLectureDTO = new MyLectureDTO();
        myLectureDTO.setId((Integer)rs.getObject("id"));
        myLectureDTO.setH1((Integer)rs.getObject("h1"));
        myLectureDTO.setH2((Integer)rs.getObject("h2"));
        myLectureDTO.setH3((Integer)rs.getObject("h3"));
        myLectureDTO.setH4((Integer)rs.getObject("h4"));
        myLectureDTO.setH5((Integer)rs.getObject("h5"));
        myLectureDTO.setH6((Integer)rs.getObject("h6"));
        myLectureDTO.setH7((Integer)rs.getObject("h7"));
        myLectureDTO.setH8((Integer)rs.getObject("h8"));
        myLectureDTO.setH9((Integer)rs.getObject("h9"));
        myLectureDTO.setH10((Integer)rs.getObject("h10"));
        myLectureDTO.setH11((Integer)rs.getObject("h11"));
        myLectureDTO.setH12((Integer)rs.getObject("h12"));
        myLectureDTO.setH13((Integer)rs.getObject("h13"));
        myLectureDTO.setH14((Integer)rs.getObject("h14"));
        myLectureDTO.setH15((Integer)rs.getObject("h15"));
        myLectureDTO.setH16((Integer)rs.getObject("h16"));
        myLectureDTO.setH17((Integer)rs.getObject("h17"));
        myLectureDTO.setH18((Integer)rs.getObject("h18"));
        myLectureDTO.setH19((Integer)rs.getObject("h19"));
        myLectureDTO.setH20((Integer)rs.getObject("h20"));
        myLectureDTO.setH21((Integer)rs.getObject("h21"));
        myLectureDTO.setH22((Integer)rs.getObject("h22"));
        myLectureDTO.setH23((Integer)rs.getObject("h23"));
        myLectureDTO.setH24((Integer)rs.getObject("h24"));
        myLectureDTO.setH25((Integer)rs.getObject("h25"));
        myLectureDTO.setH26((Integer)rs.getObject("h26"));
        myLectureDTO.setH27((Integer)rs.getObject("h27"));
        myLectureDTO.setH28((Integer)rs.getObject("h28"));
        myLectureDTO.setH29((Integer)rs.getObject("h29"));
        myLectureDTO.setH30((Integer)rs.getObject("h30"));
        myLectureDTO.setH31((Integer)rs.getObject("h31"));
        myLectureDTO.setH32((Integer)rs.getObject("h32"));
        myLectureDTO.setH33((Integer)rs.getObject("h33"));
        myLectureDTO.setH34((Integer)rs.getObject("h34"));
        myLectureDTO.setH35((Integer)rs.getObject("h35"));
        myLectureDTO.setH36((Integer)rs.getObject("h36"));
        myLectureDTO.setH37((Integer)rs.getObject("h37"));
        myLectureDTO.setH38((Integer)rs.getObject("h38"));
        myLectureDTO.setH39((Integer)rs.getObject("h39"));
        myLectureDTO.setH40((Integer)rs.getObject("h40"));
        myLectureDTO.setH41((Integer)rs.getObject("h41"));
        myLectureDTO.setH42((Integer)rs.getObject("h42"));
        myLectureDTO.setH43((Integer)rs.getObject("h43"));
        myLectureDTO.setH44((Integer)rs.getObject("h44"));
        myLectureDTO.setH45((Integer)rs.getObject("h45"));

        return myLectureDTO;
    }


    public SubjectAttendDTO setSubjectAttend(ResultSet rs) throws SQLException{
        SubjectAttendDTO subjectAttendDTO = new SubjectAttendDTO();
        Integer[] h = new Integer[45];

        int count = 23;
        for (int i = 0; i < 45; i++) {
            h[i] = (Integer)rs.getObject(count);
            count++;
        }
        subjectAttendDTO.setH(h);
        subjectAttendDTO.setStudentId(rs.getLong("student_id"));
        subjectAttendDTO.setDepartName(rs.getString("departname"));
        subjectAttendDTO.setGrade(rs.getInt("grade"));
        subjectAttendDTO.setSchoolno(rs.getString("schoolno"));
        subjectAttendDTO.setStudentState(rs.getInt("state"));
        subjectAttendDTO.setName(rs.getString("name"));
        subjectAttendDTO.setScore(rs.getInt("iattend"));
        subjectAttendDTO.setLate(rs.getInt("ilate"));
        subjectAttendDTO.setAbsent(rs.getInt("ixhour"));

        return subjectAttendDTO;
    }
}
