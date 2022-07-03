package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.DepartDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartDAOImpl extends DAOImplMySQL implements DepartDAO {

    private Connection conn;

    public DepartDAOImpl() {
        conn = getConnection();
    }

    @Override
    public int create(DepartDTO depart) {
        String query = "INSERT INTO depart VALUES (?, ?, ?, ?);";
        int rows = 0;
        try {
            PreparedStatement psm = conn.prepareStatement(query);
            psm.setInt(1, depart.getId());
            psm.setString(2, depart.getName());
            psm.setInt(3, depart.getClassNum());
            psm.setInt(4, depart.getGradeSystem());
            rows = psm.executeUpdate();

            psm.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public DepartDTO findById(int id) {
        String query = "SELECT * FROM depart WHERE id=?;";
        DepartDTO ret = new DepartDTO();
        try {
            PreparedStatement psm = conn.prepareStatement(query);
            psm.setInt(1, id);
            ResultSet rs = psm.executeQuery();

            if (rs.next()) {
                ret.setId(rs.getInt(1));
                ret.setName(rs.getString(2));
                ret.setClassNum(rs.getInt(3));
                ret.setGradeSystem(rs.getInt(4));
            }

            rs.close();
            psm.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<DepartDTO> searchByName(String name, int startRow, int size) {
        String query = "SELECT * FROM depart WHERE name LIKE ? ORDER BY name ASC LIMIT ?, ?;";
        String query2 = "SELECT * FROM depart ORDER BY name ASC LIMIT ?, ?;";
        List<DepartDTO> departList = new ArrayList<DepartDTO>();
        try {
            PreparedStatement psm;
            if (name.equals("")) {
                psm = conn.prepareStatement(query2);
                psm.setInt(1, startRow);
                psm.setInt(2, size);
            }
            else {
                psm = conn.prepareStatement(query);
                psm.setString(1, "%" + name + "%");
                psm.setInt(2, startRow);
                psm.setInt(3, size);
            }
            ResultSet rs = psm.executeQuery();

            while (rs.next()) {
                DepartDTO depart = new DepartDTO();
                depart.setId(rs.getInt(1));
                depart.setName(rs.getString(2));
                depart.setClassNum(rs.getInt(3));
                depart.setGradeSystem(rs.getInt(4));
                departList.add(depart);
            }

            rs.close();
            psm.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return departList;
    }

    @Override
    public int countByName(String name) {
        String query = "SELECT COUNT(*) FROM depart WHERE name LIKE ?;";
        String query2 = "SELECT COUNT(*) FROM depart;";
        int count = 0;
        try {
            PreparedStatement psm;
            if (name.equals("")) {
                psm = conn.prepareStatement(query2);
            }
            else {
                psm = conn.prepareStatement(query);
                psm.setString(1, "%" + name + "%");
            }
            ResultSet rs = psm.executeQuery();

            if (rs.next())
                count = rs.getInt(1);

            rs.close();
            psm.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int update(DepartDTO depart) {
        String query = "UPDATE depart SET name=?, classnum=?, gradesystem=? WHERE id=?;";
        int rows = 0;
        try {
            PreparedStatement psm = conn.prepareStatement(query);
            psm.setString(1, depart.getName());
            psm.setInt(2, depart.getClassNum());
            psm.setInt(3, depart.getGradeSystem());
            psm.setInt(4, depart.getId());
            rows = psm.executeUpdate();

            psm.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public int delete(int id) {
        String query = "DELETE FROM depart WHERE id=?;";
        int rows = 0;
        try {
            PreparedStatement psm = conn.prepareStatement(query);
            psm.setInt(1, id);
            rows = psm.executeUpdate();

            psm.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return rows;
    }
}