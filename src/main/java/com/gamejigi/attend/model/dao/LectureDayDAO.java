package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import java.util.List;

public interface LectureDayDAO {
    List<LectureDayDTO> readList(String date, int teacher_id);
    LectureDayDTO findById(Long id);
    int[] findStarthAndNormOrResthour(Long id);
    int findLectureIdById(int lectureday_id);
    LectureDayDTO findNormstartAndNormhourAndNormdate(int lecture_id);
    int findHourByLectureId(int lecture_id);
    List<LectureDayDTO> readRestList(int lecture_id);
    List<LectureDayDTO> findRestStarthAndHour(int lecture_id);
}
