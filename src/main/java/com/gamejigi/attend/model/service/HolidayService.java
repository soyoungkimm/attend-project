package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.HolidayDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface HolidayService {
    List<HolidayDTO> getHolidayList(Pagination pagination, String searcj);
    int createHoliday(HolidayDTO holidayDTO);
    HolidayDTO getHoliday(Long id);
    int deleteHoliday(Long id);
    int getHolidayTotalNum(String search);
    int updateHoliday(HolidayDTO holidayDTO);
}
