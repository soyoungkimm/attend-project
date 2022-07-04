package com.gamejigi.attend.model.dao;


import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.model.dto.RoomDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl extends DAOImplMySQL implements RoomDAO{

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public RoomDAOImpl(){
        conn=getConnection();
    }

    @Override
    public List<RoomDTO> readList() {
        ArrayList<RoomDTO> roomList  = null;

        String query = "select * from room";
        try {
            stmt = conn.createStatement();
            if((rs = stmt.executeQuery(query)) != null) {
                roomList = new ArrayList<RoomDTO>();
                while (rs.next()) {
                    RoomDTO roomDTO = new RoomDTO();
                    roomDTO = setRoom(rs);
                    roomList.add(roomDTO);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roomList;
    }

    @Override
    public List<RoomDTO> readListUsePagination(Pagination pagination) {
        ArrayList<RoomDTO> roomList  = null;
        String query = "select * from room limit ?, ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pagination.getFirstRow() - 1);
            pstmt.setInt(2, pagination.getPerPageRows());
            rs = pstmt.executeQuery();
            // execute(sql)는 모든 질의에 사용가능, executeQuery(sql)는 select에만, executeUpdate()는 insert, update, delete에 사용 가능
            roomList = new ArrayList<RoomDTO>();
            while (rs.next()) {
                RoomDTO roomDTO = new RoomDTO();
                roomDTO = setRoom(rs);
                roomList.add(roomDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roomList;
    }

    @Override
    public List<RoomDTO> readListUsePaginationAndSearch(Pagination pagination, String search) {
        ArrayList<RoomDTO> roomList = null;
        String query = "select * from room where name like ? limit ?, ?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + search + "%");
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());

            if((rs = pstmt.executeQuery()) != null){
                roomList = new ArrayList<RoomDTO>();
                while(rs.next()){
                    RoomDTO roomDTO = new RoomDTO();
                    roomDTO = setRoom(rs);
                    roomList.add(roomDTO);
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();

        }
        return roomList;
    }

    @Override
    public int create(RoomDTO roomDTO) {
        String query = "insert into room(building_id,depart_id,floor,ho,name,kind,area) values(?,?,?,?,?,?,?)";
        int rows = 0;
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, roomDTO.getBuilding_id());
            pstmt.setInt(2, roomDTO.getDepart_id());
            pstmt.setInt(3, roomDTO.getFloor());
            pstmt.setInt(4, roomDTO.getHo());
            pstmt.setString(5, roomDTO.getName());
            pstmt.setInt(6, roomDTO.getKind());
            pstmt.setInt(7, roomDTO.getArea());
            rows = pstmt.executeUpdate(); //정상: 1이상, 0이하: 에러
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public RoomDTO findById(int id) {
        RoomDTO roomDTO = new RoomDTO();
        String query = "select * from room where id=?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                roomDTO = setRoom(rs);
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roomDTO;
    }

    @Override
    public int delete(int id) {
        int rows = 0;
        String query = "delete from room where id=?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            rows=pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int update(RoomDTO roomDTO) {
        int rows= 0;
        String querry = "update room set building_id=?, depart_id=?, floor=?, ho=?, name=?, kind=?, area=? where id=?";
        try {
            pstmt = conn.prepareStatement(querry);
            pstmt.setInt(1, roomDTO.getBuilding_id());
            pstmt.setInt(2, roomDTO.getDepart_id());
            pstmt.setInt(3, roomDTO.getFloor());
            pstmt.setInt(4, roomDTO.getHo());
            pstmt.setString(5, roomDTO.getName());
            pstmt.setInt(6, roomDTO.getKind());
            pstmt.setInt(7, roomDTO.getArea());
            pstmt.setInt(8, roomDTO.getId());
            rows = pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int readTotalRowNumUseSearch(String search) {
        int totalNum = 0;
        String query = "select count(*) as num from room where name like ? ";
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

    @Override
    public int readTotalRowNum() {
        int totalNum = 0;
        String query = "select count(*) as num from room";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next()) {
                totalNum = rs.getInt("num");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return totalNum;
    }

    private RoomDTO setRoom(ResultSet rs) throws SQLException {

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(rs.getInt(1));
        roomDTO.setBuilding_id(rs.getInt(2));
        roomDTO.setDepart_id(rs.getInt(3));
        roomDTO.setFloor(rs.getInt(4));
        roomDTO.setHo(rs.getInt(5));
        roomDTO.setName(rs.getString(6));
        roomDTO.setKind(rs.getInt(7));
        roomDTO.setArea(rs.getInt(8));

        return roomDTO;
    }
}
