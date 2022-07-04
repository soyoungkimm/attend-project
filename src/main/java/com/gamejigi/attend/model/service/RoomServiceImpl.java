package com.gamejigi.attend.model.service;


import com.gamejigi.attend.model.dao.DepartDAOImpl;
import com.gamejigi.attend.model.dao.BuildingDAOImpl;
import com.gamejigi.attend.model.dao.RoomDAOImpl;

import com.gamejigi.attend.model.dto.DepartDTO;
import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.model.dto.RoomDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public class RoomServiceImpl implements RoomService {
    RoomDAOImpl roomDAO = new RoomDAOImpl();
    BuildingDAOImpl buildingDAO = new BuildingDAOImpl();
    DepartDAOImpl departDAO = new DepartDAOImpl();

    public List<BuildingDTO> getBuildingList() {
        return buildingDAO.readList();
    }
    public List<DepartDTO> getDepartList() {
        return departDAO.readList();
    }

    @Override
    public List<RoomDTO> getRoomList(Pagination pagination, String search) {
        return roomDAO.readListUsePaginationAndSearch(pagination,search);
    }

    @Override
    public int createRoom(RoomDTO roomDTO) {
        return roomDAO.create(roomDTO);
    }

    @Override
    public RoomDTO getRoom(int id) {
        return roomDAO.findById(id);
    }

    @Override
    public int deleteRoom(int id) {
        return roomDAO.delete(id);
    }

    @Override
    public int getRoomTotalNum(String search) {
        return roomDAO.readTotalRowNumUseSearch(search);
    }

    @Override
    public int updateRoom(RoomDTO roomDTO) {
        return roomDAO.update(roomDTO);
    }
}
