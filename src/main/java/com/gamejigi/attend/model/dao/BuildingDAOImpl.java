package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.util.Pagination;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildingDAOImpl extends DAOImplMySQL implements BuildingDAO{

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public BuildingDAOImpl(){
        conn=getConnection();
    }

    @Override
    public List<BuildingDTO> readList() {
        ArrayList<BuildingDTO> buildingList  = null;

        String query = "select * from building";
        try {
            stmt = conn.createStatement();
            if((rs = stmt.executeQuery(query)) != null) {
                buildingList = new ArrayList<BuildingDTO>();
                while (rs.next()) {
                    BuildingDTO building = new BuildingDTO();
                    building = setBuilding(rs);
                    buildingList.add(building);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buildingList;
    }

    @Override
    public List<BuildingDTO> readListUsePagination(Pagination pagination) {

        ArrayList<BuildingDTO> buildingList  = null;
        String query = "select * from building limit ?, ?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pagination.getFirstRow() - 1);
            pstmt.setInt(2, pagination.getPerPageRows());
            rs = pstmt.executeQuery();
            // execute(sql)는 모든 질의에 사용가능, executeQuery(sql)는 select에만, executeUpdate()는 insert, update, delete에 사용 가능
            buildingList = new ArrayList<BuildingDTO>();
            while (rs.next()) {
                BuildingDTO buildingDTO = new BuildingDTO();
                buildingDTO = setBuilding(rs);
                buildingList.add(buildingDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buildingList;
    }

    @Override
    public List<BuildingDTO> readListUsePaginationAndSearch(Pagination pagination, String search) {
        ArrayList<BuildingDTO> buildingList = null;
        String query = "select * from building where name like ? limit ?, ?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + search + "%");
            pstmt.setInt(2, pagination.getFirstRow() - 1);
            pstmt.setInt(3, pagination.getPerPageRows());

            if((rs = pstmt.executeQuery()) != null){
                buildingList = new ArrayList<BuildingDTO>();
                while(rs.next()){
                    BuildingDTO buildingDTO = new BuildingDTO();
                    buildingDTO = setBuilding(rs);
                    buildingList.add(buildingDTO);
                }
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();

        }
        return buildingList;
    }

    @Override
    public int create(BuildingDTO buildingDTO) {
        String query = "insert into building(name,floor) values(?,?)";
        int rows = 0;
        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, buildingDTO.getName());
            pstmt.setInt(2, buildingDTO.getFloor());
            rows = pstmt.executeUpdate(); //정상: 1이상, 0이하: 에러
        }catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return rows;
    }

    @Override
    public BuildingDTO findById(int id) {
        BuildingDTO buildingDTO = new BuildingDTO();
        String query = "select * from building where id=?";

        try{
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                buildingDTO = setBuilding(rs);
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return buildingDTO;
    }

    @Override
    public int delete(int id) {
        int rows = 0;
        String query = "delete from building where id=?";
        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            rows = pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int update(BuildingDTO buildingDTO) {
        int rows= 0;
        String querry = "update building set name=?, floor=? where id=?";
        try {
            pstmt = conn.prepareStatement(querry);
            pstmt.setString(1, buildingDTO.getName());
            pstmt.setInt(2, buildingDTO.getFloor());
            pstmt.setInt(3, buildingDTO.getId());
            rows = pstmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rows;
    }

    @Override
    public int readTotalRowNumUseSearch(String search) {
        int totalNum = 0;
        String query = "select count(*) as num from building where name like ? ";
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
        String query = "select count(*) as num from building";
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

    @Override
    public String getBuildingNameByRoomName(String roomName) {
        String buildingName = "";
        String query = "" +
                "select b.name " +
                "from building b " +
                "join room r on r.building_id = b.id " +
                "where r.name LIKE '%"+roomName+"%'";
        try {
            stmt = conn.createStatement();

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                buildingName = rs.getString("name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buildingName;
    }

    @Override
    public List<BuildingDTO> readListByDepart(int depart) {
        List<BuildingDTO> buildingList = new ArrayList<>();
        String query = "" +
                "select b.* from building b " +
                "join (select distinct building_id from room " +
                "where depart_id = "+depart+") r " +
                "on r.building_id = b.id " +
                "order by b.name asc";
        try {
            stmt = conn.createStatement();
            if((rs = stmt.executeQuery(query)) != null) {
                while (rs.next()) {
                    BuildingDTO building = setBuilding(rs);
                    buildingList.add(building);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return buildingList;
    }

    private BuildingDTO setBuilding(ResultSet rs) throws SQLException {

        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setId(rs.getInt(1));
        buildingDTO.setName(rs.getString(2));
        buildingDTO.setFloor(rs.getInt(3));
        return buildingDTO;
    }
}
