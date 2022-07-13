package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.LectureDayDTO;
import com.gamejigi.attend.model.dto.StLecDTO;
import com.gamejigi.attend.model.dto.StudentDTO;

import java.util.List;

public interface MyLectureDAO {
    int updateAttend(int lecture_id, int student_id, int h, int v);
    int updateAllAttend(int lectureday_id, int starth, int normhour);
    int createMyLecture(StLecDTO stLecDTO);
    int deleteMyLecture(int student_id, int lecture_id);
    int readRowNumByStudentIdAndLectureId(int student_id, int lecture_id);
}
