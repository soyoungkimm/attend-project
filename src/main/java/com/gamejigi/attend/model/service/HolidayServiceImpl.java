package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.HolidayDAOImpl;
import com.gamejigi.attend.model.dto.HolidayDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.ArrayList;
import java.util.List;

public class HolidayServiceImpl implements HolidayService{
    HolidayDAOImpl holidayDAO = null;

    public HolidayServiceImpl(){
        holidayDAO = new HolidayDAOImpl();
    }

    @Override
    public List<HolidayDTO> getHolidayList(Pagination pagination, String search) {

        return holidayDAO.readListUsePaginationAndSearch(pagination, search);
    }

    @Override
    public int createHoliday(HolidayDTO holidayDTO) {
        return holidayDAO.create(holidayDTO);
    }

    @Override
    public HolidayDTO getHoliday(Long id) {
        return holidayDAO.findById(id);
    }

    @Override
    public int deleteHoliday(Long id) {
        return holidayDAO.delete(id);
    }

    @Override
    public int getHolidayTotalNum(String search) {
        return holidayDAO.readTotalRowNumUseSearch(search);
    }

    @Override
    public int updateHoliday(HolidayDTO holidayDTO) {
        return holidayDAO.update(holidayDTO);
    }
}
