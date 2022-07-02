package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.NoticeDTO;
import com.gamejigi.attend.util.Pagination;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAOImpl extends DAOImplMySQL implements NoticeDAO {

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public NoticeDAOImpl() {
        conn = getConnection();
    }

    @Override
    public List<NoticeDTO> readList() {

        ArrayList<NoticeDTO> NoticeList  = null;

        String sql = "select * from notice";
        try {
            stmt = conn.createStatement();
            if((rs = stmt.executeQuery(sql)) != null) { // 질의 결과가 ResultSet 형태로 반환
                NoticeList = new ArrayList<NoticeDTO>();
                while (rs.next()) {
                    NoticeDTO notice = new NoticeDTO();
                    notice = setNotice(rs);
                    NoticeList.add(notice);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return NoticeList;
    }

    @Override
    public List<NoticeDTO> readListUsePagination(Pagination pagination) {

        ArrayList<NoticeDTO> noticeList  = null;
        String sql = "select * from notice limit ?, ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pagination.getFirstRow() - 1);
            pstmt.setInt(2, pagination.getPerPageRows());
            rs = pstmt.executeQuery();
            // execute(sql)는 모든 질의에 사용가능, executeQuery(sql)는 select에만, executeUpdate()는 insert, update, delete에 사용 가능
            noticeList = new ArrayList<NoticeDTO>();
            while (rs.next()) {
                NoticeDTO noticeDTO = new NoticeDTO();
                noticeDTO = setNotice(rs);
                noticeList.add(noticeDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return noticeList;
    }

    @Override
    public List<NoticeDTO> readListUsePaginationAndSearch(Pagination pagination, String search) {
        ArrayList<NoticeDTO> noticeList  = null;
        String sql = "select * from notice where title like ? limit ?, ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + search + "%");
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());

            rs = pstmt.executeQuery();
            noticeList = new ArrayList<NoticeDTO>();
            while (rs.next()) {
                NoticeDTO noticeDTO = new NoticeDTO();
                noticeDTO = setNotice(rs);
                noticeList.add(noticeDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return noticeList;
    }

    @Override
    public int create(NoticeDTO noticeDTO) {
        int rowNum = 0;
        String sql = "insert into notice(writeday, title, content) values(?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, noticeDTO.getWriteday());
            pstmt.setString(2, noticeDTO.getTitle());
            pstmt.setString(3, noticeDTO.getContent());
            rowNum = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rowNum;
    }

    @Override
    public NoticeDTO findById(Long id) {
        NoticeDTO noticeDTO = new NoticeDTO();
        String sql = "select * from notice where id=?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, String.valueOf(id));
            rs = pstmt.executeQuery();

            while(rs.next()) {
                noticeDTO = setNotice(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return noticeDTO;
    }

    @Override
    public int delete(Long id) {
        int row_num = 0;
        String sql = "delete from notice where id=?";
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
    public int readTotalRowNum() {
        int totalNum = 0;
        String sql = "select count(*) as num from notice";
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
        String sql = "select count(*) as num from notice where title like ? ";
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
    public int update(NoticeDTO noticeDTO) {
        int row_num = 0;
        String sql = "update notice set title=?, content=? where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, noticeDTO.getTitle());
            pstmt.setString(2, noticeDTO.getContent());
            pstmt.setLong(3, noticeDTO.getId());
            row_num = pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row_num;
    }

    private NoticeDTO setNotice(ResultSet rs) throws SQLException {

        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setId(rs.getLong(1));
        noticeDTO.setWriteday(rs.getString(2));
        noticeDTO.setTitle(rs.getString(3));
        noticeDTO.setContent(rs.getString(4));
        return noticeDTO;
    }

}
