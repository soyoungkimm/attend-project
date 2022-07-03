package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.BuildingDAO;
import com.gamejigi.attend.model.dao.BuildingDAOImpl;
import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public class BuildingServiceImpl implements BuildingService{

    BuildingDAOImpl buildingDAO = new BuildingDAOImpl();

    @Override
    public List<BuildingDTO> getBuildingList(Pagination pagination, String search) {
        return buildingDAO.readListUsePaginationAndSearch(pagination, search);
    }

    @Override
    public int createBuilding(BuildingDTO buildingDTO) {
        return buildingDAO.create(buildingDTO);
    }

    @Override
    public BuildingDTO getBuilding(int id) {
        return buildingDAO.findById(id);
    }
    @Override
    public int deleteBuilding(int id) {
        return buildingDAO.delete(id);
    }

    @Override
    public int getBuildingTotalNum(String search) {
        return buildingDAO.readTotalRowNumUseSearch(search);
    }

    @Override
    public int updateBuilding(BuildingDTO buildingDTO) {
        return buildingDAO.update(buildingDTO);
    }
}
