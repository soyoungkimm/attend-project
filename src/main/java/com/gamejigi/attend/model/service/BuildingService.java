package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface BuildingService {
    List<BuildingDTO> getBuildingList(Pagination pagination, String search);
    int createBuilding(BuildingDTO buildingDTO);
    BuildingDTO getBuilding(int id);
    int deleteBuilding(int id);
    int getBuildingTotalNum(String search);
    int updateBuilding(BuildingDTO buildingDTO);
}
