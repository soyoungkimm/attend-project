package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.TimetableDTO;

import java.util.List;

public interface TimetableDAO {
    List<TimetableDTO> readList();
    List<TimetableDTO> readListUseLecture(int lecture_id);
    TimetableDTO readUseId(int id);
    int create(TimetableDTO timetable);
    int update(TimetableDTO timetable);
    int delete(int id);
    int count();
    int getMaxId();
}
