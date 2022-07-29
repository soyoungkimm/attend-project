package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dao.TimeTeacherDAO;
import com.gamejigi.attend.model.dao.TimeTeacherDAOImpl;
import com.gamejigi.attend.model.dto.TimeTeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.ArrayList;
import java.util.List;

public class TimeTeacherServiceImpl implements TimeTeacherService {
    private TimeTeacherDAO timeTeacherDAO = null;
    public TimeTeacherServiceImpl() {
        timeTeacherDAO = new TimeTeacherDAOImpl();
    }

    @Override
    public List<TimeTeacherDTO> readListUsePagination(Pagination p, int year, int term, int depart) {
        List<Integer> teIdList = timeTeacherDAO.readIdListUsePagination(p, depart);
        List<TimeTeacherDTO> result = new ArrayList<>();
        for (int id : teIdList) {
            result.add(timeTeacherDAO.getTimeTeacherById(id, year, term));
        }
        return result;
    }

    @Override
    public int count(int depart) {
        return timeTeacherDAO.count(depart);
    }

    @Override
    public List<Integer> getYearList(int depart) {
        return timeTeacherDAO.getYearList(depart);
    }
}
