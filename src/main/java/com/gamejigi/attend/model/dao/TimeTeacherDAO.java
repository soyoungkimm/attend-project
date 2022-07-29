package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.TeacherDTO;
import com.gamejigi.attend.model.dto.TimeTeacherDTO;
import com.gamejigi.attend.model.dto.TimetableDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface TimeTeacherDAO {
    int count(int depart);
    List<Integer> getYearList(int depart);
    List<Integer> readIdListUsePagination(Pagination p, int depart);
    TimeTeacherDTO getTimeTeacherById(int id, int year, int term);
}
