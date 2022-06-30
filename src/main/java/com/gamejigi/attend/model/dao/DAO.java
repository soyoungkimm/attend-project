package com.gamejigi.attend.model.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public interface DAO {
    Connection getConnection();
    void closeResources(Connection conn, Statement stmt, PreparedStatement pstmt, ResultSet rs);
}
