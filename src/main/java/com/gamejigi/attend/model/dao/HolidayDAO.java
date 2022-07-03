package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.HolidayDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface HolidayDAO {
    int create(HolidayDTO holiday);
    HolidayDTO read(HolidayDTO holiday);
    HolidayDTO findById(Long id);
    List<HolidayDTO> readListUsePaginationAndSearch(Pagination pagination, String search);
    int update(HolidayDTO holiday);
    int delete(Long id);
    int readTotalRowNumUseSearch(String search);

}
