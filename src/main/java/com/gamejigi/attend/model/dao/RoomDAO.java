package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.RoomDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface RoomDAO {
    List<RoomDTO> readList();
    List<RoomDTO> readListUsePagination(Pagination pagination);
    List<RoomDTO> readListUsePaginationAndSearch(Pagination pagination, String search);
    int create(RoomDTO roomDTO);
    RoomDTO findById(int id);
    int delete(int id);
    int update(RoomDTO roomDTO);
    int readTotalRowNumUseSearch(String search);
    int readTotalRowNum();
}
