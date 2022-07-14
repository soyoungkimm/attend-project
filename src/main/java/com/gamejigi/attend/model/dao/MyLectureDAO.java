package com.gamejigi.attend.model.dao;

import com.gamejigi.attend.model.dto.MyLectureDTO2;
import com.gamejigi.attend.model.dto.SubjectAttendDTO;

import java.util.List;

public interface MyLectureDAO {
    int updateAttend(int lecture_id, int student_id, int h, int v);
    int updateAllAttend(int lectureday_id, int starth, int normhour);
    List<SubjectAttendDTO> readStudentAttendList(int lecture_id);
    MyLectureDTO2 findByLectureIdAndStudentId(int lecture_id, int student_id);
    int updateLateAndAbsentAndAttendScore(MyLectureDTO2 myLectureDTO);
    List<MyLectureDTO2> readMyLectureList(int lecture_id);
    MyLectureDTO2 findLateAbsentScore(int lecture_id, int student_id);
}
