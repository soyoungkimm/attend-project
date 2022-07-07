package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.StaffDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface StaffDAO {
    List<StaffDTO> readList();
    StaffDTO findById(int id);
    String findDepartNameByDepartId(int depart_id);
    String findPicByStaffId(int id);
    List<StaffDTO> readListUsePagination(Pagination pagination);
    List<StaffDTO> readListUsePaginationAndSearch(Pagination pagination, String search);
    int readTotalRowNum();
    int readTotalRowNumUseSearch(String search);
    int create(StaffDTO staffDTO);
    int delete(int id);
    int update(StaffDTO staffDTO);
}
