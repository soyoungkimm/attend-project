package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LoginDTO;
import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAOImpl extends DAOImplMySQL implements TeacherDAO {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public TeacherDAOImpl() {
        conn = getConnection();
    }

    @Override
    public List<TeacherDTO> readList() {
        ArrayList<TeacherDTO> teacherList  = null;
        String sql = "select * from teacher";
        try {
            stmt = conn.createStatement();
            if((rs = stmt.executeQuery(sql)) != null) {
                teacherList = new ArrayList<TeacherDTO>();
                while (rs.next()) {
                    TeacherDTO teacher = new TeacherDTO();
                    teacher = setTeacher(rs);
                    teacherList.add(teacher);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teacherList;
    }

    private TeacherDTO setTeacher(ResultSet rs) throws SQLException {

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setId(rs.getInt(1));
        teacherDTO.setDepart_id(rs.getInt(2));
        teacherDTO.setKind(rs.getInt(3));
        teacherDTO.setUid(rs.getString(4));
        teacherDTO.setPwd(rs.getString(5));
        teacherDTO.setName(rs.getString(6));
        teacherDTO.setTel(rs.getString(7));
        teacherDTO.setPhone(rs.getString(8));
        teacherDTO.setEmail(rs.getString(9));
        teacherDTO.setPic(rs.getString(10));
        teacherDTO.setDepart_name(rs.getString(11));
        return teacherDTO;
    }

    @Override
    public TeacherDTO findById(int id) {
        TeacherDTO teacherDTO = new TeacherDTO();
        String sql = "select teacher.*, depart.name as depart from teacher join depart on teacher.depart_id=depart.id where teacher.id=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(id));
            rs = pstmt.executeQuery();

            while(rs.next()) {
                teacherDTO = setTeacher(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teacherDTO;
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
    public String findPicByTeacherId(int id) {
        String pic = null;
        String sql = "select pic from teacher where id=?";
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

    @Override
    public List<TeacherDTO> readListUsePagination(Pagination pagination) {

        ArrayList<TeacherDTO> teacherList  = null;
        String sql = "select * from teacher limit ?, ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pagination.getFirstRow() - 1);
            pstmt.setInt(2, pagination.getPerPageRows());
            rs = pstmt.executeQuery();
            teacherList = new ArrayList<TeacherDTO>();
            while (rs.next()) {
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO = setTeacher(rs);
                teacherList.add(teacherDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teacherList;
    }

    @Override
    public List<TeacherDTO> readListUsePaginationAndSearch(Pagination pagination, String search) {
        ArrayList<TeacherDTO> teacherList  = null;
        String sql = "select teacher.*, depart.name as depart from teacher join depart on teacher.depart_id=depart.id where teacher.name like ? limit ?, ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + search + "%");
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());

            rs = pstmt.executeQuery();
            teacherList = new ArrayList<TeacherDTO>();
            while (rs.next()) {
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO = setTeacher(rs);
                teacherList.add(teacherDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teacherList;
    }

    @Override
    public int readTotalRowNum() {
        int totalNum = 0;
        String sql = "select count(*) as num from teacher";
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
    public int readTotalRowNumUseSearch(String search) {
        int totalNum = 0;
        String sql = "select count(*) as num from teacher where name like ? ";
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
    public int create(TeacherDTO teacherDTO) {
        int rows = 0;
        String sql = "insert into teacher(depart_id, kind, uid, pwd, name, tel, phone, email, pic) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, teacherDTO.getDepart_id());
            pstmt.setInt(2, teacherDTO.getKind());
            pstmt.setString(3, teacherDTO.getUid());
            pstmt.setString(4, teacherDTO.getPwd());
            pstmt.setString(5, teacherDTO.getName());
            pstmt.setString(6, teacherDTO.getTel());
            pstmt.setString(7, teacherDTO.getPhone());
            pstmt.setString(8, teacherDTO.getEmail());
            pstmt.setString(9, teacherDTO.getPic());
            rows = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int delete(int id) {
        int rows = 0;
        String sql = "delete from teacher where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int update(TeacherDTO teacherDTO) {
        int rows = 0;
        String sql = "update teacher set depart_id=?, kind=?, uid=?, pwd=?, name=?, tel=?, phone=?, email=?, pic=? where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, teacherDTO.getDepart_id());
            pstmt.setInt(2, teacherDTO.getKind());
            pstmt.setString(3, teacherDTO.getUid());
            pstmt.setString(4, teacherDTO.getPwd());
            pstmt.setString(5, teacherDTO.getName());
            pstmt.setString(6, teacherDTO.getTel());
            pstmt.setString(7, teacherDTO.getPhone());
            pstmt.setString(8, teacherDTO.getEmail());
            pstmt.setString(9, teacherDTO.getPic());
            pstmt.setInt(10, teacherDTO.getId());
            rows = pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    public List<TeacherDTO> readListByDepartId(int depart_id){
        ArrayList<TeacherDTO> teacherList  = null;
        String sql = "select teacher.* from teacher where depart_id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, depart_id);

            rs = pstmt.executeQuery();
            teacherList = new ArrayList<TeacherDTO>();
            while (rs.next()) {
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO.setId(rs.getInt("id"));
                teacherDTO.setDepart_id(rs.getInt("depart_id"));
                teacherDTO.setKind(rs.getInt("kind"));
                teacherDTO.setUid(rs.getString("uid"));
                teacherDTO.setPwd(rs.getString("pwd"));
                teacherDTO.setName(rs.getString("name"));
                teacherDTO.setTel(rs.getString("tel"));
                teacherDTO.setPhone(rs.getString("phone"));
                teacherDTO.setEmail(rs.getString("email"));
                teacherDTO.setPic(rs.getString("pic"));
                //teacherDTO = setTeacher(rs);
                teacherList.add(teacherDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return teacherList;
    }

    @Override
    public LoginDTO findByUidAndPwd(String uid, String pwd) {
        LoginDTO loginDTO = new LoginDTO();
        LoginDAOImpl loginDAO = new LoginDAOImpl();
        String sql = "SELECT id, uid, pwd FROM teacher where uid=? and pwd=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(uid));
            pstmt.setString(2, String.valueOf(pwd));
            rs = pstmt.executeQuery();
            if (rs.next()) {
                loginDTO = setLogin(rs);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return loginDTO;
    }

    private LoginDTO setLogin(ResultSet rs) throws SQLException {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(rs.getInt(1));
        loginDTO.setUid(rs.getString(2));
        loginDTO.setPwd(rs.getString(3));
        return loginDTO;
    }
}
