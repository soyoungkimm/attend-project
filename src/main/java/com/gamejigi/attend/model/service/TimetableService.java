package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.model.dto.RoomDTO;
import com.gamejigi.attend.model.dto.TimetableDTO;

import java.util.List;
import java.util.Map;

public interface TimetableService {
    List<TimetableDTO> readList();
    List<TimetableDTO> readListUseLecture(int lecture_id);
    TimetableDTO readUseId(int id);
    int create(TimetableDTO timetable);
    int update(TimetableDTO timetable);
    int delete(int id);
    int deleteAll(int staffId, int year, int term);
    int count();
    int getMaxId();

    int saveData(List<String> list, int staffId, int year, int term);
    List<String> loadData(int departId, int year, int term);
    List<RoomDTO> getRoomsByStaffId(int staffId);
    List<BuildingDTO> getBuildingsByRooms(List<RoomDTO> rooms);
    int getGradeSystemByStaffId(int staffId);
    List<String> getLecturesByStaffId(int staffId);
    List<String> loadDataUseTeId(int departId, int year, int term, int teacherId);
    List<String> getDataByStudentId(int student_id);
}
