package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LoginDTO;

import java.sql.*;

public class LoginDAOImpl extends DAOImplMySQL implements LoginDAO{

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public LoginDAOImpl() {
        conn = getConnection();
    }

    private LoginDTO setLogin(ResultSet rs) throws SQLException {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(rs.getInt(1));
        loginDTO.setUid(rs.getString(2));
        loginDTO.setPwd(rs.getString(3));
        return loginDTO;
    }
}
