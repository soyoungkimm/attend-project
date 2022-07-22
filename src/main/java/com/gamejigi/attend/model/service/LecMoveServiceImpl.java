package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.LectureDayDAOImpl;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public class LecMoveServiceImpl implements LecMoveService {
    LectureDayDAOImpl lectureDayDao = new LectureDayDAOImpl();
    @Override
    public List<LectureDayDTO> readLecMoveList(Pagination pagination, String s1, String s2) {
        return lectureDayDao.readListUsePaginationAndYearAndTerm(pagination, s1, s2);
    }

    @Override
    public int getLecMoveTotalNum( String s1, String s2) {
        return lectureDayDao.readTotalNumUseYearAndTerm(s1, s2);
    }
}
