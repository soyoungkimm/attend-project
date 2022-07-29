package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.TimeTeacherDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.List;

public interface TimeTeacherService {
    List<TimeTeacherDTO> readListUsePagination(Pagination p, int year, int term, int depart);
    int count(int depart);
    List<Integer> getYearList(int depart);
}
