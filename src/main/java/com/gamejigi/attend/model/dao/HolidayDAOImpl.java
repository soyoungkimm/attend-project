package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.HolidayDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HolidayDAOImpl extends DAOImplMySQL implements HolidayDAO{

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public HolidayDAOImpl(){
        conn = getConnection();
    }

    @Override
    public int create(HolidayDTO holiday) {
        String query = "insert into holiday(yyyy,holiday,reason) values(?,?,?)";
        int rows = 0;
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, holiday.getYyyy());
            pstmt.setString(2, holiday.getHoliday());
            pstmt.setString(3, holiday.getReason());
            rows = pstmt.executeUpdate(); //정상: 1이상, 0이하: 에러
        }catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return rows;
    }

    @Override
    public HolidayDTO read(HolidayDTO holiday) {
        HolidayDTO retHoliday = null;
        String query = "select * from holiday where id=?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, holiday.getId());

            rs = pstmt.executeQuery();

            if(rs.next()) {
                retHoliday = new HolidayDTO();
                retHoliday.setId(rs.getLong("id"));
                retHoliday.setYyyy(rs.getString("yyyy"));
                retHoliday.setHoliday(rs.getString("holiday"));
                retHoliday.setReason(rs.getString("reason"));
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return retHoliday;
    }

    @Override
    public HolidayDTO findById(Long id) {
        HolidayDTO retHoliday = null;
        String query = "select * from holiday where id=?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if(rs.next()) { // rs.next()는 반환된 객체에 속한 요소가 있는지를 반환하고, 다름 요소로 접근
                retHoliday = new HolidayDTO();
                retHoliday = setHoliday(rs);

            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return retHoliday;
    }

    @Override
    public List<HolidayDTO> readListUsePaginationAndSearch(Pagination pagination, String search) {

        ArrayList<HolidayDTO> holidayList = null;
        String query = "select * from holiday where yyyy like ? limit ?, ?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + search + "%");
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());

            if((rs = pstmt.executeQuery()) != null){
                holidayList = new ArrayList<HolidayDTO>();
                while(rs.next()){
                    HolidayDTO holidayDTO = new HolidayDTO();
                    holidayDTO = setHoliday(rs);
                    holidayList.add(holidayDTO);
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();

        }
        return holidayList;
    }

    @Override
    public int update(HolidayDTO holiday) {
        conn = this.getConnection();
        int rows = 0;
        String query = "update holiday set yyyy=?, holiday=?, reason=? where id=?";
        try{
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, holiday.getYyyy());
                pstmt.setString(2, holiday.getHoliday());
                pstmt.setString(3, holiday.getReason());
                pstmt.setLong(4, holiday.getId());

                rows = pstmt.executeUpdate();

        }catch (SQLException throwables){
            throwables.printStackTrace();

        }finally{
            this.closeResources(conn, stmt, pstmt, rs);

        }

        return rows;
    }

    @Override
    public int delete(Long id) {
        conn = this.getConnection();
        int rows = 0;
        String query = "delete from holiday where id=?";
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setLong(1, id);
            rows = pstmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            this.closeResources(conn, stmt, pstmt, rs);

        }

        return rows;
    }



    @Override
    public int readTotalRowNumUseSearch(String search) {
        int totalNum = 0;
        String query = "select count(*) as num from holiday where yyyy like ? ";
        try {
            pstmt = conn.prepareStatement(query);
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

    private HolidayDTO setHoliday(ResultSet rs) throws SQLException {

        HolidayDTO holidayDTO = new HolidayDTO();
        holidayDTO.setId(rs.getLong(1));
        holidayDTO.setYyyy(rs.getString(2));
        holidayDTO.setHoliday(rs.getString(3));
        holidayDTO.setReason(rs.getString(4));
        
        return holidayDTO;

    }
}
