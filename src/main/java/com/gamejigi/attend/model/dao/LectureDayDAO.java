package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.HolidayDTO;
import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.util.Pagination;

import java.util.HashMap;
import java.util.List;

public interface LectureDayDAO {
    List<LectureDayDTO> readList(String date, int teacher_id);
    LectureDayDTO findById(Long id);
    int[] findStarthAndNormOrResthour(Long id);
    int findLectureIdById(int lectureday_id);
}
