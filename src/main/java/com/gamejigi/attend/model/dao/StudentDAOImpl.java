package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl extends DAOImplMySQL implements StudentDAO {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public StudentDAOImpl() {
        conn = getConnection();
    }

    @Override
    public List<StudentDTO> readListUsePaginationAndSearch(Pagination pagination, String search) {
        ArrayList<StudentDTO> studentList  = null;
        String sql = "select student.*, depart.name as depart from student join depart on student.depart_id=depart.id where student.name like ? limit ?, ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + search + "%");
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());

            rs = pstmt.executeQuery();
            studentList = new ArrayList<StudentDTO>();
            while (rs.next()) {
                StudentDTO studentDTO = new StudentDTO();
                studentDTO = setStudent(rs);
                studentList.add(studentDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return studentList;
    }

    @Override
    public int create(StudentDTO studentDTO) {
        int rowNum = 0;
        String sql = "insert into student(depart_id, grade, class, schoolno, pwd, phone, gender, name, state, pic, email) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, studentDTO.getDepart_id());
            pstmt.setInt(2, studentDTO.getGrade());
            pstmt.setString(3, studentDTO.getBan());
            pstmt.setString(4, studentDTO.getSchoolno());
            pstmt.setString(5, studentDTO.getPwd());
            pstmt.setString(6, studentDTO.getPhone());
            pstmt.setInt(7, studentDTO.getGender());
            pstmt.setString(8, studentDTO.getName());
            pstmt.setInt(9, studentDTO.getState());
            pstmt.setString(10, studentDTO.getPic());
            pstmt.setString(11, studentDTO.getEmail());

            rowNum = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowNum;
    }

    @Override
    public StudentDTO findById(Long id) {
        StudentDTO studentDTO = new StudentDTO();
        String sql = "select student.*, depart.name as depart from student join depart on student.depart_id=depart.id where student.id=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                studentDTO = setStudent(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return studentDTO;
    }

    @Override
    public int delete(Long id) {
        int row_num = 0;
        String sql = "delete from student where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row_num;
    }

    @Override
    public int readTotalRowNumUseSearch(String search) {
        int totalNum = 0;
        String sql = "select count(*) as num from student where name like ? ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + search + "%");

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
    public int update(StudentDTO studentDTO) {
        int row_num = 0;
        String sql = "update student set depart_id=?, grade=?, class=?, schoolno=?, pwd=?, phone=?, gender=?, name=?, state=?, pic=?, email=? where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, studentDTO.getDepart_id());
            pstmt.setInt(2, studentDTO.getGrade());
            pstmt.setString(3, studentDTO.getBan());
            pstmt.setString(4, studentDTO.getSchoolno());
            pstmt.setString(5, studentDTO.getPwd());
            pstmt.setString(6, studentDTO.getPhone());
            pstmt.setInt(7, studentDTO.getGender());
            pstmt.setString(8, studentDTO.getName());
            pstmt.setInt(9, studentDTO.getState());
            pstmt.setString(10, studentDTO.getPic());
            pstmt.setString(11, studentDTO.getEmail());
            pstmt.setLong(12, studentDTO.getId());

            row_num = pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row_num;
    }

    @Override
    public String findDepartNameByDepartId(int depart_id) {
        String departName = null;
        String sql = "select name from depart where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, depart_id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                departName = rs.getString("name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return departName;
    }

    @Override
    public String findPicByStudentId(Long id) {
        String pic = null;
        String sql = "select pic from student where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pic = rs.getString("pic");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pic;
    }

    private StudentDTO setStudent(ResultSet rs) throws SQLException {

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId(rs.getLong(1));
        studentDTO.setDepart_id(rs.getInt(2));
        studentDTO.setGrade(rs.getInt(3));
        studentDTO.setBan(rs.getString(4));
        studentDTO.setSchoolno(rs.getString(5));
        studentDTO.setPwd(rs.getString(6));
        studentDTO.setPhone(rs.getString(7));
        studentDTO.setGender(rs.getInt(8));
        studentDTO.setName(rs.getString(9));
        studentDTO.setState(rs.getInt(10));
        studentDTO.setPic(rs.getString(11));
        studentDTO.setEmail(rs.getString(12));
        studentDTO.setDepart_name(rs.getString(13));

        return studentDTO;
    }


}
