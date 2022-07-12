package com.gamejigi.attend.model.service;

import com.gamejigi.attend.model.dto.BuildingDTO;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.StudentDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.HashMap;
import java.util.List;

public interface DailyAttendService {
    List<LectureDayDTO> getDailyAttendList(String date, int id);
    LectureDayDTO getLectureDayById(Long id);
    List<StudentDTO> getStudentAttend(Long lectureday_id);
    HashMap<String, Integer> getAllAttend(Long lectureday_id);
    int updateAttend(int lecture_id, int student_id, int h, int v);
    int updateAllAttend(int lecture_id);
}