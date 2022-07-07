package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.StaffDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAOImpl extends DAOImplMySQL implements StaffDAO {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public StaffDAOImpl() {
        conn = getConnection();
    }

    @Override
    public List<StaffDTO> readList() {
        ArrayList<StaffDTO> staffList  = null;
        String sql = "select * from staff";
        try {
            stmt = conn.createStatement();
            if((rs = stmt.executeQuery(sql)) != null) {
                staffList = new ArrayList<StaffDTO>();
                while (rs.next()) {
                    StaffDTO staff = new StaffDTO();
                    staff = setStaff(rs);
                    staffList.add(staff);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return staffList;
    }

    private StaffDTO setStaff(ResultSet rs) throws SQLException {

        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setId(rs.getInt(1));
        staffDTO.setDepart_id(rs.getInt(2));
        staffDTO.setUid(rs.getString(3));
        staffDTO.setPwd(rs.getString(4));
        staffDTO.setName(rs.getString(5));
        staffDTO.setTel(rs.getString(6));
        staffDTO.setPhone(rs.getString(7));
        staffDTO.setEmail(rs.getString(8));
        staffDTO.setPic(rs.getString(9));
        staffDTO.setDepart_name(rs.getString(10));
        return staffDTO;
    }

    @Override
    public StaffDTO findById(int id) {
        StaffDTO staffDTO = new StaffDTO();
        String sql = "select staff.*, depart.name as depart from staff join depart on staff.depart_id=depart.id where staff.id=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(id));
            rs = pstmt.executeQuery();

            while(rs.next()) {
                staffDTO = setStaff(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return staffDTO;
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
    public String findPicByStaffId(int id) {
        String pic = null;
        String sql = "select pic from staff where id=?";
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
    public List<StaffDTO> readListUsePagination(Pagination pagination) {

        ArrayList<StaffDTO> staffList  = null;
        String sql = "select * from staff limit ?, ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pagination.getFirstRow() - 1);
            pstmt.setInt(2, pagination.getPerPageRows());
            rs = pstmt.executeQuery();
            staffList = new ArrayList<StaffDTO>();
            while (rs.next()) {
                StaffDTO staffDTO = new StaffDTO();
                staffDTO = setStaff(rs);
                staffList.add(staffDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return staffList;
    }

    @Override
    public List<StaffDTO> readListUsePaginationAndSearch(Pagination pagination, String search) {
        ArrayList<StaffDTO> staffList  = null;
        String sql = "select staff.*, depart.name as depart from staff join depart on staff.depart_id=depart.id where staff.name like ? limit ?, ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + search + "%");
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());

            rs = pstmt.executeQuery();
            staffList = new ArrayList<StaffDTO>();
            while (rs.next()) {
                StaffDTO staffDTO;
                staffDTO = setStaff(rs);
                staffList.add(staffDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return staffList;
    }

    @Override
    public int readTotalRowNum() {
        int totalNum = 0;
        String sql = "select count(*) as num from staff";
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
        String sql = "select count(*) as num from staff where name like ? ";
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
    public int create(StaffDTO staffDTO) {
        int rows = 0;
        String sql = "insert into staff(depart_id, uid, pwd, name, tel, phone, email, pic) values(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, staffDTO.getDepart_id());
            pstmt.setString(2, staffDTO.getUid());
            pstmt.setString(3, staffDTO.getPwd());
            pstmt.setString(4, staffDTO.getName());
            pstmt.setString(5, staffDTO.getTel());
            pstmt.setString(6, staffDTO.getPhone());
            pstmt.setString(7, staffDTO.getEmail());
            pstmt.setString(8, staffDTO.getPic());
            rows = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int delete(int id) {
        int rows = 0;
        String sql = "delete from staff where id=?";
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
    public int update(StaffDTO staffDTO) {
        int rows = 0;
        String sql = "update staff set depart_id=?, uid=?, pwd=?, name=?, tel=?, phone=?, email=?, pic=? where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, staffDTO.getDepart_id());
            pstmt.setString(2, staffDTO.getUid());
            pstmt.setString(3, staffDTO.getPwd());
            pstmt.setString(4, staffDTO.getName());
            pstmt.setString(5, staffDTO.getTel());
            pstmt.setString(6, staffDTO.getPhone());
            pstmt.setString(7, staffDTO.getEmail());
            pstmt.setString(8, staffDTO.getPic());
            pstmt.setInt(9, staffDTO.getId());
            rows = pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

}

