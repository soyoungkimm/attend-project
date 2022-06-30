package com.gamejigi.attend.model.dao;

import java.sql.*;

public class DAOImplMySQL implements DAO {
    private Connection conn = null;
    @Override
    public Connection getConnection() {
        String jdbcUrl = "jdbc:mysql://localhost:포트/DB이름";
        String dbUser = "아이디";
        String dbPass = "비밀번호";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    @Override
    public void closeResources(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs) {

    }
}
