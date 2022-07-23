package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface BuildingDAO {
    List<BuildingDTO> readList();
    List<BuildingDTO> readListUsePagination(Pagination pagination);
    List<BuildingDTO> readListUsePaginationAndSearch(Pagination pagination, String search);
    int create(BuildingDTO buildingDTO);
    BuildingDTO findById(int id);
    int delete(int id);
    int update(BuildingDTO buildingDTO);
    int readTotalRowNumUseSearch(String search);
    int readTotalRowNum();
    String getBuildingNameByRoomName(String roomName);
    List<BuildingDTO> readListByDepart(int depart);
}
