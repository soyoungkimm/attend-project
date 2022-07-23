package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.util.Pagination;

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
    List<LectureDayDTO> readListUsePaginationByTeacher(Pagination pagination, int id);
    int getTotalRowsByTeacher(int id);
    int getTotalRows();
    void updateForLecMove(LectureDayDTO lectureDay);

    List<LectureDayDTO> readRestListUsePaginationAndDepartId(Pagination pagination, int depart_id);
    int readTotalRowNumUseDepartID(int depart_id);
    int updateStateById(Long id, int state);

    List<LectureDayDTO> readListUsePaginationAndYearAndTerm(Pagination pagination, String year , String term);
    int readTotalNumUseYearAndTerm(String year, String term);
}
